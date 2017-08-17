package com.leopard.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Database {

	private static final String baseubuntu = "[12:17:18] < ActionParsnip1> JabberWokky: do hard links stay?[12:17:26] < afancy> jscinoz:  it is NVIDIA Quadro NVS 135M[12:17:31] :: Home page for #ubuntu: http://www.ubuntu.com[12:17:35] :: HHH (n=HHH@host4-205-dynamic.36-79-r.retail.telecomitalia.it) has joined #ubuntu[12:17:44] < jscinoz> afancy: and when you installed the nvidia driver the first time which one did you install??[12:17:44] < JabberWokky> ActionParsnip1: Can't hardlink across mountpoints.[12:17:44] :: pguillaum (n=chatzill@lns-bzn-47f-62-147-214-115.adsl.proxad.net) has joined #ubuntu[12:17:47] < jscinoz> ?*[12:17:48] < Appocc> durt, lsof: WARNING: can't stat() fuse.gvfs-fuse-daemon file system /home/elisah/.gvfs[12:17:49] < ManateeLazyCat> Looks DNS wrong with Ubuntu 8.10, how to fix?[12:17:49] :: voidmage (n=voidmage@su19.eastnet.gatech.edu) has quit (Read error: 104 (Connection reset by peer))[12:17:49] < Appocc>       Output information may be incomplete.[12:17:51] < ActionParsnip1> JabberWokky: bah[12:17:53] :: totimkopf (n=james@unaffiliated/totimkopf) has quit (Client Quit)[12:18:10] < ActionParsnip1> ManateeLazyCat: add some dns servers to /etc/resolv.conf[12:18:11] :: m1r (n=m1r0@78-2-90-206.adsl.net.t-com.hr) has joined #ubuntu[12:18:12] :: issa_ (n=issa@78.154.165.95) has joined #ubuntu[12:18:29] < afancy> jscinoz: i cannot remember, i think i in stalled  it by systamtic[12:18:42] :: Leevi (n=leevi@a91-156-78-131.elisa-laajakaista.fi) has joined #ubuntu[12:18:55] :: dios_mio (i=test@88.243.196.132) has quit (Connection timed out)[12:19:01] < jscinoz> afancy: can you do 'sudo apt-get install --reinstall nvidia-glx-legacy'[12:19:17] < ManateeLazyCat> ActionParsnip1: I did it, setup static ip, and add \"192.168.1.1\" to dns in /etc/resolv.conf just can connect when i setup, it will failed when i restart system.[12:19:19] :: Appocc (n=elisah@20158210116.user.veloxzone.com.br) has quit (Remote closed the connection)[12:19:36] :: Leevi (n=leevi@a91-156-78-131.elisa-laajakaista.fi) has quit (Remote closed the connection)[12:19:39] < _infidel> the \"usbmodules\" command is a way to determine which modules are required for a particular device. Is there something similar in ubuntu?[12:19:52] < ActionParsnip1> ManateeLazyCat: did you add it in the correct synatax[12:19:54] :: kernel (n=kernel@218.149.109.14) has joined #ubuntu[12:19:54] :: kernel (n=kernel@218.149.109.14) has quit (Client Quit)[12:19:56] < BABER> ActionParsnip1: how can find ports?[12:19:58] < ManateeLazyCat> ActionParsnip1: I don't know why Ubuntu 8.10 can't work like Debian, i have two machine, one is Debian, one is Ubuntu 8.10[12:19:58] < afancy> jscinoz: i think so, but when i go into the recovery mode, how can it enable the network connection in command line?[12:19:58] < durt> Appocc, hmm, don't think that's got any thing to do with alsa or pulseaudio.[12:19:59] :: chadwik (n=ch4dw1k@cpc2-hitc3-0-0-cust51.lutn.cable.ntl.com) has joined #ubuntu[12:20:00] :: kernel (n=kernel@218.149.109.14) has joined #ubuntu[12:20:03] :: jimijix (n=radix@125.160.107.48) has joined #ubuntu[12:20:06] < dan-ubuntu> anyone know how to configure an apple bluetooth wireless aluminum keyboard with 8.10? i keep getting 'pairing failed'[12:20:13] < ManateeLazyCat> ActionParsnip1: Yep, after i add it, it can work, but failed when i reboot.[12:20:14] < ActionParsnip1> ManateeLazyCat: you need to add the word 'nameserver' to the beginning of it[12:20:24] < ManateeLazyCat> ActionParsnip1: Yep, i did[12:20:35] < ActionParsnip1> BABER: ports of what, can you expand on your vague question[12:20:35] :: phuzion (n=phuzion@cpe-173-89-180-36.neo.res.rr.com) has joined #ubuntu[12:20:40] < jimijix> hi, currently i'm dual-booting ubuntu and vista using grub; i'm going to install windows xp how do i do it? just install it?[12:21:04] < phuzion> I'm listening to a stream online, and the flash container is REALLY crappy, is there a way to find the direct stream URL I can throw into VLC or something?[12:21:15] < ActionParsnip1> jimijix: install it but you will need to reinstall grub if you are installing to the same disk that has grub on[12:21:17] < BABER> ActionParsnip1: ports that are opened or i have used them[12:21:21] < ManateeLazyCat> ActionParsnip1: First is setup static IP in /etc/network/interfaces, and remove \"inet dhcp\", and add \"namesever 192.168.1.1\" in /etc/resolv.conf[12:21:25] < ActionParsnip1> BABER: netstat -a[12:21:30] < ManateeLazyCat> ActionParsnip1: But now it can't work again";
	private static final String basejava = "[13:24:54] <rsra>how can I find the proper position to append text to a HTMLDocument (in HTMLEditorKit)?[13:25:23] <zjjvs->what is the proper position ?[13:25:26] <zjjvs->add a child to the parent element you want to insert stuff itno[13:26:24] <zjjvs->eg the body[13:26:56] <rsra>yes ... thats where it should end up[13:26:32] <rsra>is there a method getElementsByTagName for HTMLDocument or how do I find body element?[13:26:30] <rsra>I only see getElement for id and getElement for a specific attribute ... but no way to find for element by name.[13:28:31] <zjjvs->doc.getIterator(HTML.Tag.BODY) or so[13:28:33] <rsra>roots-: blind me ... thanks![13:28:33] <myrrmrmrrr>It seems a stack of 200 bytes is not big enough for me! :([13:35:41] <gudedj>yo[13:35:41] <gudedj>ugh... java does not has a lazy interpretation ?![13:36:42] <gudedj>ie if (expr1 && expr2) { } if expr1 is false, will expr2 get evaluated ?[13:36:42] <gudedj>because looking at a snippet I'm debugging, it seems it does...[13:37:43] <ef_crlyvrg>it won't[13:38:44] <ef_crlyvrg>if(asd != null && asd.do()) works ok[13:38:45] <gudedj>well, then how do you explain that given that I wrote : if (generator.nextBoolean() && nbr-- > 0) { ... } printing nbr shows that nbr gets decremented though the { ... } is not executed[13:40:45] <gudedj>anyway, I moved the nbr--; to the bloc[13:40:46] <gudedj>but weird, though :)[13:42:47] <ryfnrm>Guyzmo: maybe first part of condition was true?[13:42:47] <gudedj>fsck[13:42:47] <gudedj>no[13:42:48] <gudedj>maybe last part was false[13:44:48] <gudedj>you're right[13:44:48] <gudedj>never code again before full wake up[13:45:48] <gudedj>..[13:45:48] <ryfnrm>:)[13:45:53] <dfdrc>Is there anoyne who is familliar with hibernate?[13:48:54] <dfdrc>What's the differencs between save() and persist()? Why should I call update() if updation is automatic? Should I use it for detached objects only?[13:48:59] <zdzzd_zfzzzsjg>Guyzmo: You haven't had your morning garlic yet, I see.[13:50:59] <gudedj>lol :)[14:50:09] <myrrmrmrrr>YES![14:00:09] <myrrmrmrrr>I love the feeling when you crush a bug./[14:00:19] <mggvrfmrgrr>hi anybody working on jsf custom components?[14:10:20] <zdzzd_zfzzzsjg>~dll_search_order is <see>dll_search_order[14:10:20] <rrvr2jv>Okay, ricky_clarkson.[14:15:20] <zdzzd_zfzzzsjg>~no, dll_search_order is <see>~dll_search_order[14:15:20] <rrvr2jv>Okay, ricky_clarkson.[14:16:20] <sygjrw>roots-, and theres my answer...in java's bin directory theres a jpeg.dll :D[14:16:20] <mggvrfmrgrr>hi anybody working on jsf custom components?[14:17:21] <sygjrw>now theres a problem here...java clearly needs that DLL...and SDL needs another jpeg.dll....issues ;)[14:17:23] <zjjvs->so ?[14:18:23] <zjjvs->check the search order[14:18:24] <sygjrw>well accordining to the search order it looks in the current directory first[14:19:24] <zjjvs->unless a custom order is supplied[14:19:24] <sygjrw>which is where the jpeg.dll SDL is interested in is! :D[14:21:25] <zjjvs->or if the dll was loaded before you are out of luck as well[14:21:25] <zjjvs->why use SDL anyhow ?";

	// The demo user of the project.
	private User demoUser = null;
	private List<String[]> channelUbuntu = new ArrayList<String[]>();
	private List<String[]> channelJava = new ArrayList<String[]>();

	Connection c = null;
	PreparedStatement ps = null;
	
	public Database() {

		channelUbuntu = parse(baseubuntu);
		channelJava = parse(basejava);

	}

	/*
	 * Return the channel log as a String for simplicity and to force the
	 * controllers to parse the information.
	 */
	public List<String[]> getLog(String channelName) {

		if (channelName == "#java") {
			return channelJava;
		} else {
			return channelUbuntu;
		}
	}

	/*
	 * Returns the user object corresponding to the given login name. In this
	 * case we only have one, return it.
	 */
	public User getUser(String fname, String lname) {
		// "Look up" the user from the database.
		demoUser = new User(fname, lname);
		return demoUser;		
		
	}

	private List<String[]> parse(String channel) {

		List<String[]> result = new ArrayList<String[]>();

		// Parse the log.
		String[] rows = channel.split("\\[");

		for (String row : rows) {
			if(row.equals("")) continue;
			String[] cols = row.split("\\]");
			result.add(new String[] { cols[0], cols[1] });
		}
		return result;
	}
}
