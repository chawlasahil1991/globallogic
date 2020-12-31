package com.globallogic.GlobalLogic;

import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import com.globallogic.model.Presenter;

public class AppTest1 {

	@Test
	public void testLoadDataFromCSVFile() {
		List<Presenter> list = Main.loadPresentersFromCSVFile();
		Assert.assertNotNull(list);
	}

	@Test
	public void testPresentersExists() {
		List<Presenter> list = Main.loadPresentersFromCSVFile();
		TreeMap<Integer, List<Presenter>> treeMap = Main.convertListToTreeMap(list);
		int conferenceHours = 8;
		List<List<Presenter>> result = Main.getPresentersIn3Sessions(conferenceHours, treeMap);
		boolean noPresenterFound = Main.isNoPresenterFound(result);
		Assert.assertFalse(noPresenterFound);
	}

	@Test
	public void testNoPresentersExists() {
		TreeMap<Integer, List<Presenter>> treeMap = new TreeMap<>();
		int conferenceHours = 8;
		List<List<Presenter>> result = Main.getPresentersIn3Sessions(conferenceHours, treeMap);
		boolean noPresenterFound = Main.isNoPresenterFound(result);
		Assert.assertTrue(noPresenterFound);
	}
}
