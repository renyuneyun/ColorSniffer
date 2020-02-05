# ColorSniffer

------

Get and export color codings for your apps.

This app is intended to be used as a companion app for [Last Launcher](https://github.com/SubhamTyagi/Last-Launcher), as a easy method to obtain custom while regular coloring of apps.

## Data format

Currently it uses [TSV](https://en.wikipedia.org/wiki/Tab-separated_values). The header of the table is (not included in the exported data):

```
activityId	HexColor
```

- The `activityId` is the `ApplicationInfo.name` value
	- It ought to be `packageName.ActivityName`
- The `HexColor` is returned from `ColorDrawable.color` and formatted with `%X`
	- It ought to be in CAPITAL
	- It ought to have **8** numbers
	- It ought to be in the form of `AARRGGBB`

## TODO

- [ ] Wait for Last Launcher to support importing from clipboard (or similar, changing accordingly)
- [ ] Provide a better UX (to be called from Last Launcher), possibily through `startActivityForResult()` and return the data through `Intent`?
- [ ] A more focused UI
- [ ] Support coloring apps by groups (user-defined or imported / from clipboard)
- [ ] Support a more intuitive workflow to set app colors (i.e. choose how and what to change in this app and then call Last Launcher to set colors)

