package com.borui.cucumber;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = "src/test/recourses",
        glue = {"com.borui.cucumber"},
        monochrome = true,
        plugin = { "pretty"}
)
public class CucumberRunner {
}