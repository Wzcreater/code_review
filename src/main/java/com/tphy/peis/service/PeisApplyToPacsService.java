package com.tphy.peis.service;

import com.tphy.peis.entity.dto.PeisToPacsGetReportDTO;
import com.tphy.peis.entity.dto.PeisToPacsSqdjDTO;

import java.io.IOException;
import java.net.MalformedURLException;

public interface PeisApplyToPacsService {
    Boolean applyToAdd(String examNum);

    String updateStatus(PeisToPacsSqdjDTO peisToPacsSqdjDTO);

    String postReport(PeisToPacsGetReportDTO peisToPacsGetReportDTO) throws IOException;
}
