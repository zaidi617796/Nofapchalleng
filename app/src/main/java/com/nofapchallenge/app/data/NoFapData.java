package com.nofapchallenge.app.data;

/**
 * Saara content ek jagah — assets/ me hardcode nahi, taake quotes
 * aasani se add ho sakein. (app-data-nofap.md, verified 18 Oct 2026)
 */
public class NoFapData {

    public static class Quote {
        public final String textUr;
        public final String category; // himmat | temptation | relapse | deeni
        public final String sourceRef; // null ya "Q24:30" | "Muslim1400" | "DuaShakl"

        public Quote(String textUr, String category, String sourceRef) {
            this.textUr = textUr;
            this.category = category;
            this.sourceRef = sourceRef;
        }
    }

    public static final Quote[] QUOTES = new Quote[]{
            new Quote("Nazar jhukana kamzori nahi, sab se bari taqat hai.", "himmat", null),
            new Quote("Jo apni nazar par qabu pa le, wo apni zindagi par qabu pa leta hai.", "himmat", null),
            new Quote("Tumhare khayalat tumhari qaid nahi — tum un ke malik ho.", "himmat", null),
            new Quote("Har temptation ek test hai, aur har jeet ek darja.", "temptation", null),
            new Quote("Aaj ka ek din, kal ke pachtawe se behtar hai.", "temptation", null),
            new Quote("Camera band ho sakta hai, lekin Allah dekh raha hai — aur yehi tumhari taqat hai.", "temptation", null),
            new Quote("Ghalti insan se hoti hai; haar manna shaitan ka kaam hai — dobara utho.", "relapse", null),
            new Quote("Girna ghalti hai, lete rehna ghalti hai. Aaj phir se shuru karo.", "relapse", null),
            new Quote("Roza sirf bhook ka nahi, nafs ka bhi ilaj hai.", "deeni", "Muslim1400"),
            new Quote("Chhoti jeet, bari jeet ki buniyad hoti hai — ek din, phir ek din.", "deeni", null),
    };

    public static class Milestone {
        public final int day;
        public final String textUr;

        public Milestone(int day, String textUr) {
            this.day = day;
            this.textUr = textUr;
        }
    }

    public static final Milestone[] MILESTONES = new Milestone[]{
            new Milestone(1, "Pehla qadam — Allah tumhare irade ko dekhta hai. Aaj se nayi shuruaat."),
            new Milestone(3, "3 din! Wohi kaam jo kal mushkil tha, aaj aadat ban raha hai."),
            new Milestone(7, "Ek hafta mukammal — tum ne khud ko sabit kar diya ke control mumkin hai."),
            new Milestone(14, "2 hafte! Nafs ab tumhara sathi ban raha hai, dushman nahi."),
            new Milestone(21, "21 din — aadat ki buniyad rakh di. Ab peeche mat dekho."),
            new Milestone(30, "Ek mahina! Ye woh jeet hai jo koi nahi dekhta, lekin Allah dekhta hai."),
            new Milestone(60, "60 din — tum woh shaks ban rahe ho jo tum banna chahte the."),
            new Milestone(90, "90 din! Classic reboot milestone — ab sab se mushkil hissa peeche hai."),
            new Milestone(120, "120 din — ab ye ladayi nahi, ye tumhari pehchan hai."),
            new Milestone(180, "6 mahine! Kabhi namumkin lagta tha, aaj ye tumhari taqat hai."),
            new Milestone(365, "Ek saal mukammal. Mubarak ho — tum ne khud ko badal diya."),
    };

    // --- Emergency (SOS) dua — Shakl ibn Humayd (ra) se marwi; Abu Dawood 1551 ---
    public static final String DUA_AR =
            "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ شَرِّ سَمْعِي، وَمِنْ شَرِّ بَصَرِي، وَمِنْ شَرِّ لِسَانِي، وَمِنْ شَرِّ قَلْبِي، وَمِنْ شَرِّ مَنِيِّي";
    public static final String DUA_UR =
            "Aye Allah! Main teri panah mangta hun apne kaan ke shar se, apni aankh ke shar se, " +
            "apni zaban ke shar se, apne dil ke shar se, aur apni khwahish ke shar se.";
    public static final String DUA_EN =
            "O Allah, I seek refuge in You from the evil of my hearing, my sight, my tongue, my heart, and my desire.";

    public static final String[] SOS_ACTIONS = new String[]{
            "Thanda pani piyo ya wudu karo",
            "10 push-ups lagao ya thori walk karo",
            "Phone kamre se bahar rakho — kisi ke paas ja kar betho",
            "Qur'an ki tilawat suno (2–3 minute)",
            "Roze ka irada karo — roza nafs ke liye dhal hai",
    };

    public static final String SOS_CALM =
            "Nazar jhukana — yehi tumhari jeet hai. (Surah An-Nur 24:30 ka khulasa)";

    // --- Daleel section ---
    public static final String R1_AR =
            "قُل لِّلْمُؤْمِنِينَ يَغُضُّوا مِنْ أَبْصَارِهِمْ وَيَحْفَظُوا فُرُوجَهُمْ ۚ ذَٰلِكَ أَزْكَىٰ لَهُمْ ۗ إِنَّ اللَّـهَ خَبِيرٌ بِمَا يَصْنَعُونَ";
    public static final String R1_UR =
            "Eman walon se kaho ke apni nigahein neechi rakhein aur apni sharamgahon ki hifazat " +
            "karein; ye un ke liye zyada pakeezah hai. Beshak Allah un ke aamal se bakhabar hai. " +
            "(Mafhoom — Surah An-Nur 24:30)";
    public static final String R1_EN =
            "\"Tell the believing men to reduce [some] of their vision and guard their private parts. " +
            "That is purer for them. Indeed, Allah is [fully] Aware of what they do.\" (Sahih International, 24:30)";

    public static final String R2_EN =
            "\"O young men, whoever among you can afford it, let him get married, for it is more effective " +
            "in lowering the gaze and guarding one's chastity. And whoever cannot afford it should fast, " +
            "for it will be a shield for him.\" (Sahih Muslim 1400)";
    public static final String R2_UR =
            "Aye nojawano! Jo nikah ki taqat rakhta hai wo nikah kare — ye nazar jhukane aur " +
            "paakdamni ki hifazat me zyada mu'assir hai. Aur jo taqat na rakhta ho, wo roze rakhe — " +
            "roza us ke liye dhal hai. (Sahih Muslim 1400 — mafhoom)";

    public static final String R4_EN =
            "\"Fear Allah wherever you are, and follow up a bad deed with a good deed and it will wipe it out, " +
            "and behave well towards people.\" (Tirmidhi)";
    public static final String R4_UR =
            "Jahan bhi ho Allah se daro, aur buri baat ke baad achi neki karo — wo use mita degi, " +
            "aur logon se achay akhlaq se pesh aao. (Tirmidhi — mafhoom)";

    public static final String DISCLAIMER_MEDICAL =
            "Ye app motivation ke liye hai, medical advice nahi. Shadeed addiction par kisi counselor ya doctor se rujoo karein.";
    public static final String DISCLAIMER_RELIGIOUS =
            "Ye app koi deeni authority nahi — references mashhoor tarjumaat se liye gaye hain.";
}
