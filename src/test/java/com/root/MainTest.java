package com.root;

import org.junit.Test;

public class MainTest {

    @Test
    public void mainTest() throws Exception {

        String [] repositories = new String[] {
                "AlienVault-Labs/AlienVaultLabs",
                "AlienVault-OTX/OTX-Apps-TAXII",
                "AlienVault-OTX/OTX-Python-SDK"
        };

        Main.main(repositories);
    }
}