
# Exercise 2

Welcome to the second hexagonal architecture session.

We want to extend our existing Pizzeria application.   

The business has improved, and we now want new shops in some regions to use (fake) AWS to hold our data.

The legacy repository will still exist for old shops, so we want to have both of these.

We also want to introduce a new business rule: prices in pizzas must be bigger than 0.
- If we find a pizza that invalidates this, we want to throw an error upstream.
- If we are updating a pizza to 0 or less we also want to throw an error.

 
-----  



We have PizzaNotValidException that can be thrown for this. Controller handling has already been added, but we need to decide when and how to throw this exception.
- Make the tests in `PizzaApiResourceIT.java`, `PizzaApiResourceTest.java` pass
- Make the final test in `PizzaManagerTest` pass

  
------  
We have imported some libraries for S3 support, S3BucketClient.java and S3ObjectSummary.java . We now need to configure a new adapter to use these.

- Implement the `S3PizzaRepository.java` .
- Make the tests in `PizzaApiResourceIT.java` run with the new Adapter.

You can tell if its using the new adapter by looking at the logs during the test.