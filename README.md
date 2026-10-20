# NoFap Challenge (Urdu) — v1

7-app plan ki **teesri app** (order: Settings Guide → Bijli Bill → NoFap).
Urdu me NoFap challenge helper — English apps me community/streak hai,
**Urdu me koi app nahi** (competition brief me verify).

## Features (v1 — lean MVP)
- **Streak counter** — bara day counter, SharedPreferences me save
- **Roz ka quote** — 10 Urdu quotes, roz rotate hote hain
- **🆘 Emergency (SOS)** — mustanad dua (Arabic + Urdu + English) + 5 actions + 5-minute timer
- **🏆 Milestones** — 11 targets (1, 3, 7 … 365 din), reached/unreached
- **📖 Daleel** — Surah An-Nur 24:30, Sahih Muslim 1400, Tirmidhi (verified refs)
- **🔄 Relapse flow** — no-shame confirm dialog, streak dobara

## Tech
- Java, AppCompat, minSdk 23, targetSdk 34
- **Zero permissions** — sab kuch local
- Content `data/NoFapData.java` me (hardcode nahi, quotes add ho sakte hain)

## Build
GitHub Actions (`build.yml`) — sirf debug job, taake `secrets` wali parser error na aaye.
Push par **NoFapChallenge-debug** APK Artifacts me milega.

## Policy notes
- Koi explicit imagery/graphic language nahi — clean motivational design
- "Medical advice nahi" disclaimer home par; "not a religious authority" Daleel me
