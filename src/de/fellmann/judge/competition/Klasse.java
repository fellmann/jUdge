package de.fellmann.judge.competition;

public class Klasse {
	private static String formation_names[] = {"Formation"};
	private static String formation_short[] = {"F"};
	public static Klasse Formation = new Klasse(1, formation_names, formation_short, "Formation", "f");

	private static String latein3_names[] = {"ChaCha", "Rumba", "Jive"};
	private static String latein3_short[] = {"CCC", "RB", "JV"};
	public static Klasse Latein3 = new Klasse(3, latein3_names, latein3_short, "Latein (3 Tänze)", "l3");

	private static String latein4_names[] = {"Samba", "ChaCha", "Rumba", "Jive"};
	private static String latein4_short[] = {"SB", "CCC", "RB", "JI"};
	public static Klasse Latein4 = new Klasse(4, latein4_names, latein4_short, "Latein (4 Tänze)", "l4");

	private static String latein5_names[] = {"Samba", "ChaCha", "Rumba", "Pasodoble", "Jive"};
	private static String latein5_short[] = {"SB", "CCC", "RB", "PD", "JI"};
	public static Klasse Latein5 = new Klasse(5, latein5_names, latein5_short,"Latein (5 Tänze)", "l5");
	
	private static String standard3_names[] = {"Langsamer Walzer", "Tango", "Quickstep"};
	private static String standard3_short[] = {"LW", "TA", "QS"};
	public static Klasse Standard3 = new Klasse(3, standard3_names, standard3_short,"Standard (3 Tänze)", "s3");

	private static String standard4_names[] = {"Langsamer Walzer", "Tango", "Slowfox", "Quickstep"};
	private static String standard4_short[] = {"LW", "TA", "SF", "QS"};
	public static Klasse Standard4 = new Klasse(4, standard4_names, standard4_short,"Standard (4 Tänze)", "s4");

	private static String standard5_names[] = {"Langsamer Walzer", "Tango", "Wiener Walzer", "Slowfox", "Quickstep"};
	private static String standard5_short[] = {"LW", "TA", "WW", "SF", "QS"};
	public static Klasse Standard5 = new Klasse(5, standard5_names, standard5_short, "Standard (5 Tänze)", "s5");

	private static String zehn_names[] = {"Langsamer Walzer", "Tango", "Wiener Walzer", "Slowfox", "Quickstep", "Samba", "ChaCha", "Rumba", "Pasodoble", "Jive"};
	private static String zehn_short[] = {"LW", "TA", "WW", "SF", "QS", "SB", "CCC", "RB", "PD", "JI"};
	public static Klasse Zehn_Taenze = new Klasse(10, zehn_names, zehn_short, "Zehn Tänze", "z");
	
	public static Klasse Klassen[] = {Formation, Latein3, Latein4, Latein5, Standard3, Standard4, Standard5, Zehn_Taenze};
	
	private int dances;
	private String[] name;
	private String[] shorty;
	private String title, id;
	
	public static Klasse get(String id) {
		for(int i=0;i<Klassen.length;i++) {
			if(Klassen[i].getID().equals(id)) {
				return Klassen[i];
			}
		}
		return null;
	}
	
	private Klasse(int dances, String[] names, String[] shorts, String title, String id) {
		this.id = id;
		this.dances = dances;
		name = names;
		shorty = shorts;
		this.title = title; 
	}

	public int getDances() {
		return dances;
	}
	
	public String getID() {
		return id;
	}
	
	public String getDanceShortName(int dance) {
		if(dance >=0 && dance < dances) {
			return shorty[dance];
		}
		else {
			return "";
		}
	}
	
	public String getDanceName(int dance) {
		if(dance >=0 && dance < dances) {
			return name[dance];
		}
		else {
			return "";
		}
	}
	
	public String toString() {
		return title;
	}
	
	public String getTitle() {
		return title;
	}
}
