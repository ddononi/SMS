// ž�޴�

function tm_on(id) {
	if (id == b_id) return false;

	// ���õȸ޴� ��Ȱ��ȭ
	if (b_id) {
		var b_otm = document.getElementById("tm_"+b_id);
		var b_otma = document.getElementById("tm_"+b_id+"_a");
		var b_osm = document.getElementById("s_tm_"+b_id);
		if (b_id != "mail") {
			b_otm.className = "tmnu";
		}
		b_otma.className = "m01";
		b_osm.style.visibility = "hidden";

		// �����ʸ޴� Bar Ȱ��ȭ
		var b_idr = get_idr(b_id);
		if (b_idr) {
			var b_otmr = document.getElementById("tm_"+b_idr);
			b_otmr.className = "tmnu";
		}
	}

	// ����޴� Ȱ��ȭ
	var otm = document.getElementById("tm_"+id);
	var otma = document.getElementById("tm_"+id+"_a");
	var osm = document.getElementById("s_tm_"+id);
	otm.className = "tmnu_on";
	otma.className = "m01 on";
	osm.style.visibility = "visible";

	// �����ʸ޴� Bar ��Ȱ��ȭ
	var idr = get_idr(id);
	if (idr) {
		var otmr = document.getElementById("tm_"+idr);
		otmr.className = "tmnu_on";
	}

	// ���õȸ޴� ��ü
	b_id = id;
}

function get_idr(id) {
	switch (id) {
		case "mail":
			return "message";
		case "message":
			return "mileage";
		case "mileage":
			return "premium";
		case "premium":
			return "team";
		case "team":
			return "note";
		case "note":
			return "address";

		default:
			return false;
	}
}