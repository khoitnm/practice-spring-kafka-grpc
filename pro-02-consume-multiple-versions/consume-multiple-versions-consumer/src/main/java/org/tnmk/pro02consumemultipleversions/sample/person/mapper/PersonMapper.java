package org.tnmk.pro02consumemultipleversions.sample.person.mapper;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV01Proto;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV02Proto;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV01;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV02;

public class PersonMapper {

    public static PersonV01 toPersonV01(PersonV01Proto proto){
        PersonV01 personV01 = new PersonV01();
        personV01.setFullName(proto.getRealName());
        personV01.setNickName(proto.getNickName());
        return personV01;
    }

    public static PersonV02 toPersonV02(PersonV02Proto proto){
        PersonV02 personV02 = new PersonV02();
        personV02.setFirstName(proto.getFirstName());
        personV02.setLastName(proto.getLastName());
        personV02.setNickName(proto.getNickName());
        return personV02;
    }
}
