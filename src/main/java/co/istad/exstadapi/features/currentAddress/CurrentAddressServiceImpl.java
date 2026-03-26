package co.istad.exstadapi.features.currentAddress;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.CurrentAddress;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressRequest;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressResponse;
import co.istad.exstadapi.mapper.CurrentAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentAddressServiceImpl implements CurrentAddressService {

    private final CurrentAddressRepository currentAddressRepository;
    private final CurrentAddressMapper currentAddressMapper;

    @Override
    public List<CurrentAddressResponse> getAllCurrentAddresses() {
        List<CurrentAddress> currentAddresses = currentAddressRepository.findAllByIsDeletedFalse();
        return currentAddresses.stream().map(
                currentAddressMapper::fromCurrentAddress
        ).toList();
    }

    @Override
    public CurrentAddressResponse getCurrentAddressByUuid(String uuid) {
        return currentAddressMapper.fromCurrentAddress(
               currentAddressRepository.findByUuid(uuid).orElseThrow(
                        () -> new RuntimeException("Current address not found")
                )
        );
    }

    @Override
    public CurrentAddressResponse createCurrentAddress(CurrentAddressRequest currentAddressRequest) {
        CurrentAddress currentAddress = currentAddressMapper.toCurrentAddress(currentAddressRequest);
        currentAddress.setIsDeleted(false);
        currentAddress.setUuid(UUID.randomUUID().toString());
        return currentAddressMapper.fromCurrentAddress(currentAddressRepository.save(currentAddress));
    }

    @Transactional
    @Override
    public BasedMessage deleteCurrentAddressByUuid(String uuid) {
        if (!currentAddressRepository.existsByUuid(uuid)) {
            throw new RuntimeException("Current address not found");
        }
        currentAddressRepository.softDeleteByUuid(uuid);
        return new BasedMessage("Current address deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteCurrentAddressByUuid(String uuid) {
        if (!currentAddressRepository.existsByUuid(uuid)) {
            throw new RuntimeException("Current address not found");
        }
        currentAddressRepository.deleteByUuid(uuid);
        return new BasedMessage("Current address hard deleted successfully");
    }
}
