package com.gitcolab.services;

import org.mockito.Mock;

import com.gitcolab.repositories.UserRepository;

public class ExploreServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ExploreService exploreService;
q
    public void testLoadUserByUsername() {
        String username = "sampleUsername";

        // Run the test
        exploreService.loadUserByUsername(username);
    }


}
