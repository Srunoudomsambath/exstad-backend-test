package co.istad.exstadapi.features.currentAddress;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressRequest;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressResponse;

import java.util.List;

public interface CurrentAddressService {
    List<CurrentAddressResponse> getAllCurrentAddresses();
    CurrentAddressResponse getCurrentAddressByUuid(String uuid);
    CurrentAddressResponse createCurrentAddress(CurrentAddressRequest currentAddressRequest);
    BasedMessage deleteCurrentAddressByUuid(String uuid);
    BasedMessage hardDeleteCurrentAddressByUuid(String uuid);
}
