package com.raquel.hexagon.infrastructure;

import com.raquel.hexagon.domain.PizzeriaRepositoryIT;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import com.raquel.hexagon.infrastructure.outputAdapter.ArrayListPizzeriaRepository;

class ArrayListPizzeriaRepositoryIT extends PizzeriaRepositoryIT {

    @Override
    protected PizzeriaRepository getPizzeriaRepositoryUnderTest() {
        return new ArrayListPizzeriaRepository();
    }
}