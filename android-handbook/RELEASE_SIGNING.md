# Android release signing

The workflow always publishes a directly downloadable debug APK to the GitHub
Release after a change reaches `main`. This debug build is useful for trying the
app, but it is not a stable update channel because its CI signing key is temporary.

The workflow publishes an install-over-update release APK only when all four
GitHub Actions secrets are configured. The keystore is a long-lived signing
identity: back it up securely and never commit it to this repository.

## 1. Create the keystore locally

Run this on a trusted machine with JDK 17 or newer:

```sh
keytool -genkeypair -v \
  -keystore swift-pocket-guide-release.jks \
  -alias swift-pocket-guide \
  -keyalg RSA -keysize 2048 -validity 10000
```

Choose and securely save the keystore password and key password when prompted.
Do not lose the keystore: future APK updates must use the same key.

## 2. Add GitHub Actions secrets

In the repository, open **Settings → Secrets and variables → Actions → New repository secret**.
Add these four secrets:

| Secret | Value |
| --- | --- |
| `ANDROID_KEYSTORE_BASE64` | Base64 encoding of the entire `.jks` file |
| `ANDROID_KEYSTORE_PASSWORD` | Keystore password |
| `ANDROID_KEY_ALIAS` | `swift-pocket-guide` |
| `ANDROID_KEY_PASSWORD` | Password for the key alias |

On Linux, get the base64 value with:

```sh
base64 -w 0 swift-pocket-guide-release.jks
```

On PowerShell:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("swift-pocket-guide-release.jks"))
```

Paste the output as the value of `ANDROID_KEYSTORE_BASE64`. Do not paste passwords
or keystore contents into issues, pull requests, source files, or chat.

## 3. Install the first permanently signed build

The already-published v1.1.0 APK was signed with a temporary CI debug key. Android
cannot update it with a different signing key. For the one-time transition, uninstall
v1.1.0 and install the first v1.1.1 APK signed with this keystore. Every subsequent
release signed with the same keystore can update the installed app normally.

The pull-request workflow builds and tests a debug APK as a CI artifact. After the
change is merged into `main`, the workflow publishes
`Swift-Pocket-Guide-v1.1.1-debug.apk` as a direct GitHub Release download (not an
Actions ZIP artifact). If the four signing secrets are present, it also attaches the
signed release APK to that same release.
