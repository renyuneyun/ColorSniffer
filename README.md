# ColorSniffer

------

Get and export color codings for your apps.

This app is intended to be used as a companion app for [Last Launcher](https://github.com/SubhamTyagi/Last-Launcher), as a easy method to obtain custom while regular coloring of apps.

## Usage

Depending on your opinion, there are two approaches. Please also read the *Data format* section for details of how the data are stored/presented.

### To clipboard

You can copy/export the color scheme from the app to the clipboard, and paste/import it anywhere suitable.

### By Intent (`startActivityForResult()`)

On an app which requires the color scheme, you can do a `startActivityForResult()` call to the `ryey.colorsniffer.FormActivity`, and listen to its result (through `onActivityResult()`). The color scheme will be passed with the `Intent`.

## Data format

The exact plan to store the data differs by the method, but there are something in common:

- The `activityId` is the `ActivityInfo.name` value
	- It is `packageName.ActivityName`
- The `packageName` is the `ActivityInfo.packageName` value
- The `color` is an `Integer` and corresponds to the value returned from `ColorDrawable.color`
	- It is in the form of `AARRGGBB` (bitwise)


### Clipboard

It uses [TSV](https://en.wikipedia.org/wiki/Tab-separated_values). The header of the table is (not included in the exported data):

```
packageName	#HexColor
```

- The `packageName` is defined above
- The `#HexColor` is the `String` hex representation of the `color` (returned from `ColorDrawable.color`) and formatted with `#%X`
	- It starts with `#`
	- It ought to be in CAPITAL
	- It ought to have **8** (hex) digits

### Intent

The information is stored in the `Bundle extras` of the `Intent`:

- Each `packageName` is the key
- Each `color` is the value

## TODO

- [ ] Wait for Last Launcher to support importing from clipboard (or similar, changing accordingly)
- [x] Provide a better UX (to be called from Last Launcher), possibily through `startActivityForResult()` and return the data through `Intent`?
- [ ] A more focused UI
- [ ] Support coloring apps by groups (user-defined or imported / from clipboard)
- [ ] Support a more intuitive workflow to set app colors (i.e. choose how and what to change in this app and then call Last Launcher to set colors)

