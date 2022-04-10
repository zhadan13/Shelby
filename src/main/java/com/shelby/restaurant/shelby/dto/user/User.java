package com.shelby.restaurant.shelby.dto.user;

import com.shelby.restaurant.shelby.dto.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String email;

    private String phoneNumber;

    private String password;

    private String firstName;

    private String lastName;

    private UserRole role;

    private Address address;

    private Boolean locked;

    private Boolean enabled;
}
