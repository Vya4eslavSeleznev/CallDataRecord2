package com.nexign.brt.service;

import com.nexign.brt.exception.BalanceLessThanZeroException;

public interface CallDataRecordEventListener {

    void processMessage(String cdrModel) throws BalanceLessThanZeroException, InterruptedException;
}
