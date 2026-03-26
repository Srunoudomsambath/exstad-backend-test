package co.istad.exstadapi.features.currentAddress;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressRequest;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/current-addresses")
public class CurrentAddressController {

    private final CurrentAddressService currentAddressService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCurrentAddresses() {
        return new ResponseEntity<>(
                Map.of("currentAddresses",currentAddressService.getAllCurrentAddresses()), HttpStatus.OK);
    }

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CurrentAddressResponse getCurrentAddress(@PathVariable String uuid) {
        return currentAddressService.getCurrentAddressByUuid(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrentAddressResponse createCurrentAddress(@RequestBody CurrentAddressRequest currentAddressRequest) {
        return currentAddressService.createCurrentAddress(currentAddressRequest);
    }

    @PutMapping("{uuid}/soft-delete")
    @ResponseStatus(HttpStatus.OK)
    public BasedMessage deleteCurrentAddress(@PathVariable String uuid) {
        return currentAddressService.deleteCurrentAddressByUuid(uuid);
    }


    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public BasedMessage hardDeleteCurrentAddress(@PathVariable String uuid) {
        return currentAddressService.hardDeleteCurrentAddressByUuid(uuid);
    }

}
