package com.globallogic.GlobalLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.globallogic.model.Presenter;

public class Main {
	private static final String csvFile = "src/main/resources/presenters.csv";
	private static final String delimiter = ",";

	public static List<Presenter> loadPresentersFromCSVFile() {
		List<Presenter> list = new ArrayList<>();
		try {
			File file = new File(csvFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] tempArr;
			while ((line = br.readLine()) != null) {
				tempArr = line.split(delimiter);
				String name = tempArr[0];
				int hours = Integer.parseInt(tempArr[1]);
				int fees = Integer.parseInt(tempArr[2]);
				list.add(new Presenter(name, hours, fees));
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return list;
	}

	public static TreeMap<Integer, List<Presenter>> convertListToTreeMap(List<Presenter> list) {
		TreeMap<Integer, List<Presenter>> treeMap = new TreeMap<>();
		for (Presenter p : list) {
			if (treeMap.get(p.getHours()) == null) {
				treeMap.put(p.getHours(), new ArrayList<Presenter>());
			}
			treeMap.get(p.getHours()).add(p);
		}
		return treeMap;
	}

	public static List<List<Presenter>> getPresentersIn3Sessions(int conferenceHours,
			TreeMap<Integer, List<Presenter>> treeMap) {
		List<List<Presenter>> list = new ArrayList<>();

		if (conferenceHours % 3 == 0) {
			int perSessionHours = conferenceHours / 3;
			for (int i = 0; i < 3; i++) {
				List<Presenter> presenters = getPresenters(perSessionHours, treeMap);
				list.add(presenters);
			}
		} else {
			int rem = conferenceHours % 3;
			int roundSum = conferenceHours + 3 - rem;
			int perSessionHours = roundSum / 3;
			/*
			 * conferenceHours=8, rem=2, perSessionHours=(8+3-2)/3 =9
			 */
			for (int i = 0; i < 2; i++) {
				List<Presenter> presenters = getPresenters(perSessionHours, treeMap);
				list.add(presenters);
			}
			List<Presenter> presenters = getPresenters(perSessionHours - (roundSum - conferenceHours), treeMap);
			list.add(presenters);
		}
		return list;
	}

	public static List<Presenter> getPresenters(int perSessionHours, TreeMap<Integer, List<Presenter>> treeMap) {
		List<Presenter> list = new ArrayList<>();
		Comparator<Presenter> sortByFees = (p1, p2) -> p1.getFees() > p2.getFees() ? 1
				: (p1.getFees() < p2.getFees() ? -1 : 0);

		for (Map.Entry<Integer, List<Presenter>> entry : treeMap.entrySet()) {
			List<Presenter> valueList = entry.getValue();
			Collections.sort(valueList, sortByFees);
			Iterator<Presenter> iterator = valueList.iterator();

			while (iterator.hasNext()) {
				Presenter presenter = iterator.next();
				if (perSessionHours - presenter.getHours() < 0)
					break;
				list.add(presenter);
				perSessionHours -= presenter.getHours();
				iterator.remove();
			}

		}
		return list;
	}

	public static boolean isNoPresenterFound(List<List<Presenter>> result) {
		if (result.get(0).size() == 0 && result.get(1).size() == 0 && result.get(2).size() == 0)
			return true;
		return false;
	}

	public static void main(String[] args) {
		List<Presenter> list = loadPresentersFromCSVFile();
		TreeMap<Integer, List<Presenter>> treeMap = convertListToTreeMap(list);
		int conferenceHours = 8;
		List<List<Presenter>> result = getPresentersIn3Sessions(conferenceHours, treeMap);
		boolean noPresenterFound = isNoPresenterFound(result);
		if (noPresenterFound)
			System.out.println("No Presenters Found");
		else
			System.out.println(result);
	}
}
