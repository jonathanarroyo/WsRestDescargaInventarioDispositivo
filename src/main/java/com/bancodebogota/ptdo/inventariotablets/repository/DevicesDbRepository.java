package com.bancodebogota.ptdo.inventariotablets.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bancodebogota.ptdo.inventariotablets.model.Device;

public interface DevicesDbRepository extends MongoRepository<Device, String> {

	Device findById(ObjectId _id);

	Optional<List<Device>> findByCostCenter(String costCenter);

	@Query("{'imeiOrMeid' : '?0'}")
	Optional<Device> findByImeiOrMeid(String imeiOrMeid);
}
