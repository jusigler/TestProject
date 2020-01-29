package com.leaf;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot";
    }

    @RequestMapping("/logstuff")
    public String logStuff() {

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User called log stuff").build()
        );

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("hello@leaf").build()
        );

        // Add extra data to future events in this context.
        Sentry.getContext().addExtra("extra", "thing");

        // Add an additional tag to future events in this context.
        Sentry.getContext().addTag("tagName", "tagValue");

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("This is a test");

        return "Logged Stuff";
    }

    @RequestMapping("/throw")
    public String throwException() throws Exception {
        throw new Exception("throw an exception");
    }
}
