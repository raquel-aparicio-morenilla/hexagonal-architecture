package com.raquel.hexagon.infrastructure.outputAdapter;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import io.quarkus.arc.profile.IfBuildProfile;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
//Use this quarkus.profile to enable this bean.
@IfBuildProfile("aws-repository")
public class s3PizzeriaRepository implements PizzeriaRepository {
    S3BucketClient s3BucketClient = new S3BucketClient("s3-london-shop-central");


    @Override
    public List<Pizza> getAllPizzas() {
        return null;
    }

    @Override
    public Optional<Pizza> getPizza(String name) {
        return Optional.empty();
    }

    @Override
    public void storePizza(Pizza pizza) {

    }

    @Override
    public void resetSystem() {

    }
}
