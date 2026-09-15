# Running MyCards on this Mac

Two parts: the Android app (`MyCards/`) and the AI proxy (`mycards-ai-proxy/`).

## 0. One-time fix right after unzip
The build file was renamed to `.txt` so the zip could pass email filters. Restore it:

```bash
mv MyCards/gradle/wrapper/gradle-wrapper.jar.txt MyCards/gradle/wrapper/gradle-wrapper.jar
chmod +x MyCards/gradlew
```
(You can ignore/delete `gradlew.bat.txt` — that's the Windows launcher; the Mac uses `gradlew`.)

## 1. The Android app
1. Make sure **Android Studio is up to date** (Quail 2 / 2026.1.2+). The project uses AGP 9.3 / Kotlin 2.2, so an older Studio will fail to sync.
2. **Android Studio → Open →** select the `MyCards` folder.
3. It creates `local.properties` automatically (the Mac's SDK path). Let it.
4. **Gradle Sync** runs — needs internet; downloads Gradle 9.5 + libraries the first time (a few minutes).
5. No emulator yet? **Tools → Device Manager → + → Create Virtual Device → Pixel →** download a system image → **Finish**.
6. Press **▶ Run**.

## 2. The AI proxy
Install uv if needed:
```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```
Then:
```bash
cd mycards-ai-proxy
uv sync
cp .env.example .env
open -e .env            # paste your ANTHROPIC_API_KEY, save, close
uv run uvicorn main:app --host 0.0.0.0 --port 8000 --reload
```
Check: open http://localhost:8000/health → `{"status":"ok"}`. Leave this Terminal running.

## 3. Demo it
- Proxy running + app running.
- The app reaches the proxy at `http://10.0.2.2:8000` (emulator → this Mac). No change needed.
- Tap a card → transactions → a transaction → detail. Tap **Ask AI** → e.g. *"How much did I spend on dining?"*
- Top-right **🌙/☀️** icon toggles dark/light for the whole app.

## Notes
- AI model is `claude-haiku-4-5` (in `mycards-ai-proxy/main.py`).
- `.env` is intentionally NOT in this zip (your key stays private) — recreate it in step 2.
