package service;

import com.google.inject.ImplementedBy;

@ImplementedBy(TransferServiceImpl.class)
public interface TransferService {

    void transfer(Long from, Long to, Double amount);
}
