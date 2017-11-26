# Trade validator (Kotlin)
### goal
this started as [just another coding challenge][oldProject] which I was given, but then I decided to learn Kotlin with it  

I will not list any details of this project here, because it's covered in the original repository.  
Here I'll just focus on Kotlin.
### running

to run this project one just has to run the main function in **Application** class  

    fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
    }

service is available on two endpoints: 
- http://localhost:8080/trades  
- http://localhost:8080/bulkTrades  
  
easiest way to check it out is using Swagger UI tool:  
http://localhost:8080/documentation  

### thoughts after implementing that project in kotlin
- in some places (for example `Trade` class and classes extending it) I had to make some workaround to fit original needs, which were pretty specific to begin with. That means 
that despite Kotlin being almost null-safe I had to introduce tons of nulls, because validation logic required that.  
- other weird thing is making classes extending `Trade` data classes. I just wanted to play around with them and that is why everything is in the constructor. And Spock tests 
have now more complicated factory methods... oh well. At least now I know that `jackson-module-kotlin` can handle them :)  
- `ValidationResult` is a weird place. I just wanted to play around with sealed classes. Feel free to comment about how bad is that design :D  
- I found a nice [kotlin wrapper for SLF4J][kotlinLogging]. But it lacked SLF4J-ext support, so I added it in [my fork][kotlinLoggingExt].  
This is how you can declare a logger in Kotlin now:  
```kotlin
    companion object : KLogging()
```
and use methods like those:
```kotlin
    LOG.entry(input)
    
    return LOG.exit(ValidationResult.success())
```
- `sequence API` (I think it's called like that) is very similar to `Stream API` from Java 8, but a bit easier on eyes.  
For example:
```java
    List<ValidationResult> findValidationErrors(Stream<ValidationResult> results) {
        return results.filter(ValidationResult::isFailure).collect(Collectors.toList());
    }
```
vs
```kotlin
    fun findValidationErrors(results: Collection<ValidationResult>) = results.filter { it.isFailure() }

```
- I am a fan of `when` keyword. It may not be as powerful as in Scala, but it's good enough for me :) Check this out:  
```java
    if (isA3rdFridayOfAQuarter(input.getValueDate())) {
        return LOG.exit(ValidationResult.success());
    } else {
        return LOG.exit(ValidationResult.failure("the value date should be a 3rd Friday of a quarter", VALUE_DATE_FIELD_NAME));
    }
```
vs
```kotlin
    return when {
        isA3rdFridayOfAQuarter(input.valueDate) -> LOG.exit(SuccessValidationResult)
        else -> LOG.exit(GenericFailureValidationResult("the value date should be a 3rd Friday of a quarter", VALUE_DATE_FIELD_NAME))
    }
```
- `swagger` even works.  
- `metrics` are also visible. just open this and you'll see: http://localhost:8080/metrics/priv.rdo.trade.endpoint.TradeValidationEndpoint.trades.*   
- everything works. that is amazing. I thought that moving a project to a different language will be harder... kotlin FTW I suppose :)  

#### note
One thing I noticed after a while is that this project has kotlin, java and groovy in it. And it just works.  
This `build.gradle` file has just 84 lines and covers all that.   
```
3 languages working together with almost no configuration.
```
If that is not awesome, I have no idea what is... :)     

[oldProject]: https://github.com/WrRaThY/trade-validator
[kotlinLogging]: https://github.com/MicroUtils/kotlin-logging
[kotlinLoggingExt]: https://github.com/WrRaThY/kotlin-logging-ext