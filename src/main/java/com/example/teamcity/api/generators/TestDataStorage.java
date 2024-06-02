package com.example.teamcity.api.generators;

import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import com.example.teamcity.api.specs.Specifications;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;
    private List<String> createdProjecstList;
    private List<String> createdUsersList;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
        this.createdProjecstList = new ArrayList<>();
        this.createdUsersList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }

    public TestData addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
        return testData;
    }

    public void addCreatedProjectId(String projectId) {
        getStorage().createdProjecstList.add(projectId);
    }

    public void addCreatedUserName(String userName) {
        getStorage().createdUsersList.add(userName);
    }

    public void delete() {
        testDataList.forEach(TestData::delete);
    }

    public void deleteCreated() {
        var spec = Specifications.getSpec().superUserSpec();
        createdProjecstList.forEach(id -> new UncheckedProject(spec).delete(id));
        createdUsersList.forEach(name -> new UncheckedUser(spec).delete(name));
    }
}
