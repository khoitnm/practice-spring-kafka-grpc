package org.tnmk.pro02consumemultipleversions.sample.person.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV01;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV02;

@Service
public class PersonService {
    public static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    public void logPersonV01(PersonV01 personV01) {
        logger.info("Receive PersonV01:\n '{}'", personV01);
    }

    public void logPersonV02(PersonV02 personV02) {
        logger.info("Receive PersonV02:\n '{}'", personV02);
    }
}
