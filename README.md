# ColorSniffer

Get and export color codings for your apps.

This app is intended to be used as a companion app for [Last Launcher](https://github.com/SubhamTyagi/Last-Launcher), as a easy method to obtain custom while regular coloring of apps.

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/ryey.colorsniffer/)

[Download from the release page.](https://github.com/renyuneyun/ColorSniffer/releases)

## Usage

Depending on your opinion, there are two approaches. Please also read the *Data format* section for details of how the data are stored/presented.

### To clipboard

You can copy/export the color scheme from the app to the clipboard, and paste/import it anywhere suitable.

### By Intent (`startActivityForResult()`)

On an app which requires the color scheme, you can do a `startActivityForResult()` call to the `ryey.colorsniffer.FormActivity`, and listen to its result (through `onActivityResult()`). The color scheme will be passed with the `Intent`.

## Output data format

The exact plan to store the data differs by the method, but there are something in common:

- The `launcherId` is in the form of `appId/activityId`:
   - `appId` is the application id, extracted from `ActivityInfo.packageName`
   - `activityId` is the (fully-quantified) activity name, extracted from `ActivityInfo.name`
- The `packageName` is the `ActivityInfo.packageName` value
- The `color` is an `Integer` and corresponds to the value returned from `ColorDrawable.color`
   - It is in the form of `AARRGGBB` (bitwise)

Note the data formats may change, based on the discussion between the author of Last Launcher and me (watch the issues).

### Clipboard

It uses [TSV](https://en.wikipedia.org/wiki/Tab-separated_values). The header of the table is (not included in the exported data):

```
launcherId	#HexColor
```

- The `launcherId` is defined above
- The `#HexColor` is the `String` hex representation of the `color` (returned from `ColorDrawable.color`) and formatted with `#%X`
   - It starts with `#`
   - It ought to be in CAPITAL
   - It ought to have **8** (hex) digits

### Intent

The information is stored in a `Bundle`, using the following method:

- `"color_bundle"`: `Bundle` where in the `Bundle`:
   - `launcherId`: `color`
   - `"default_color_for_apps"`: `color` (`int`)

That means, the `Bundle` stores the colors of each app in a nested `Bundle` (with key `color_bundle`), using `packageName` as key and `color` as value; the `Bundle` also contains a key `default_color_for_apps` whose value is a color representing the default color (for all apps not in the list).

## Potential future changes of data format

### (Input) Accept the "current" color

Last Launcher may add (using the same way as ColorSniffer outputs) an extra `Bundle` in the `Intent` to specify the current app colors.

This is to be done after ColorSniffer has the ability to "ignore" apps.

### (Output) `Intent`-based passing

For the `Intent`-based method, I'm hoping to change it to the following:

- `"color_bundle"`: `Bundle` where in the `Bundle`:
   - `launcherId`: `color`
- `"default_color_for_apps"`: `color` (`int`)


## TODO

- [x] Wait for Last Launcher to support importing from clipboard (or similar, changing accordingly)
- [x] Provide a better UX (to be called from Last Launcher), possibily through `startActivityForResult()` and return the data through `Intent`?
- [ ] A more focused UI
- [ ] Support coloring apps by groups (user-defined or imported / from clipboard)
- [ ] Support a more intuitive workflow to set app colors (i.e. choose how and what to change in this app and then call Last Launcher to set colors)

## License

Copyright 2020 renyuneyun (Rui Zhao)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


