package database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import database.entity.EmployeeEntity;

public class EmployeeWithTask {

    @Embedded
    public EmployeeEntity employee;

    @Relation(parentColumn =" email", entityColumn ="owner", entity = EmployeeEntity.class)
    public List<EmployeeEntity> employees;

}
