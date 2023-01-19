package com.raquel.hexagon;

class ArrayListPizzeriaRepositoryIT extends PizzeriaRepositoryIT{

    @Override
    protected PizzeriaRepository getPizzeriaRepositoryUnderTest() {
        return new ArrayListPizzeriaRepository();
    }
}