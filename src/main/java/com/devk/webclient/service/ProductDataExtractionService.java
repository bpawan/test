package com.devk.webclient.service;

import com.devk.webclient.lifefactory.client.ContractInformationClient;
import com.devk.webclient.lifefactory.client.exception.TransactionalServiceClientException;
import com.devk.webclient.service.exception.ProductDataExtractionServiceFailedException;
import com.devk.webclient.service.exception.UnsupportedInsuranceTypeException;
import com.devk.webclient.service.model.ProductData;
import com.devk.webclient.service.util.ProductTypeMapper;
import com.fja.xmlns.ipl.ccshared.lb.types.types.CVSlvVermRolle;
import com.fja.xmlns.ipl.ccshared.lc.sc.infofilterdef.types.HvGruppeInfo;
import com.fja.xmlns.ipl.ccshared.lc.sc.infofilterdef.types.LvInfo;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.fja.xmlns.ipl.ccshared.lb.types.types.CVSlvVermRolle.ABSCHLUSSVERMITTLER_1;

@Service
@RequiredArgsConstructor
public class ProductDataExtractionService {
    public static final String MODIFICATION_REASON_CUSTOMER_REQUIREMENT = "CUSTOMER_REQUIREMENT";

    @Autowired
    private final ContractInformationClient contractInformationClient;

    @Autowired
    private final ProductTypeMapper productTypeMapper;

    public ProductData extractFromContractInformation(
            LocalDate effectiveDate,
            String contractNumber
    ) throws ProductDataExtractionServiceFailedException {

        try {
            LvInfo result = fetchLvInfoFromClient(effectiveDate, contractNumber);

            return ProductData
                    .builder()
                    .brokerData(this.extractBrokerData(result.getPrv()))
                    .directInsuranceFeature(result.getJurLV().getKzbAV().toString())
                    .currentContribution(extractCurrentContribution(result.getHvGruppe()))
                    .productType(this.productTypeMapper.map(result.getPdId()))
                    .build();
        } catch (TransactionalServiceClientException | UnsupportedInsuranceTypeException exception) {
            throw new ProductDataExtractionServiceFailedException(exception);
        }
    }

    private LvInfo fetchLvInfoFromClient(
            LocalDate effectiveDate,
            String contractNumber
    ) throws TransactionalServiceClientException {

        SoContractCalculateInformationResponse response = this.contractInformationClient.fetchContractInformation(
                effectiveDate,
                contractNumber,
                MODIFICATION_REASON_CUSTOMER_REQUIREMENT
        );

        return response.getResult();
    }


    private String extractCurrentContribution(HvGruppeInfo hvGruppeInfo) {
        return String.valueOf(hvGruppeInfo.getBtg() - hvGruppeInfo.getUebverr());
    }


    private List<String> extractBrokerData(LvInfo.Prv prv) {
        return prv
                .getPrv()
                .stream()
                .map(prvInfo -> {
                    CVSlvVermRolle rollId = prvInfo.getRollenId();
                    if (rollId.equals(ABSCHLUSSVERMITTLER_1) || rollId.equals(CVSlvVermRolle.ABSCHLUSSVERMITTLER_2)) {
                        return prvInfo.getAgenturIdExt();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
