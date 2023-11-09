# docs-gen
 A markdown docs preview in browser using ktor server

## Download

You can download the latest version of the `docs-gen` command line tool in [release](https://github.com/szkug/docs-gen/releases)

It work on a yaml config file.

## Yaml Config File

When you exc `docs-gen` comand, it will find the `config.yml` in the current execution directory.

You can also specify the target config file using `docs-gen mydocs/config.yml`.

The yaml configuration file supports the following arguments.

| key | type | description |
| --- | ---- | ----------- |
| root | String | Base working path, the relative path to this configuration file. |
| static | String | Static resource directory relative the root path. |
| flavour | String | Markdown flavour, the value is **GitHub** or **JetBrains**. |
| docs | List\<String> | Markdown files order. Relative the root path. |

If you have a `workspace` dir like the following:

```
workspace
  - docs
    - topics
      - Docs1.md
      - Docs2.md
      - Docs0.md
    - images
```

You can create a configuratio file `docs-gen-config.yml` in workspace, and the content as follow.

```yaml
root: docs
static: images
flavour: GitHub
docs:
  - topics/Docs0.md
  - topics/Docs1.md
  - topics/Docs2.md
```

Then the `workspace` link the following:

```
workspace
  - docs
    - topics
    - images
  - docs-gen-config.yml
```

Now enter `cd workspace` on the command line and execute `docs-gen docs-gen-config.yml`.

The browser address for the markdown document preview service will be entered.

```
2023-11-09 21:42:55.735 [main] INFO  ktor.application - Exc env = [workspace]
2023-11-09 21:42:55.747 [main] INFO  ktor.application - Config file = docs-gen-config.yml
2023-11-09 21:42:55.785 [main] INFO  ktor.application - Root dir = [workspace]
2023-11-09 21:42:55.785 [main] INFO  ktor.application - Config = Config(root=docs, static=images, flavour=GitHub, title=Docs, docs=[***.md])
2023-11-09 21:42:55.830 [main] INFO  ktor.application - Autoreload is disabled because the development mode is off.
2023-11-09 21:42:55.851 [main] INFO  ktor.application - Application started in 0.038 seconds.
2023-11-09 21:42:55.947 [DefaultDispatcher-worker-1] INFO  ktor.application - Responding at http://0.0.0.0:8080
```

Now you can open `http://0.0.0.0:8080` in your browser to read the markdown document.
