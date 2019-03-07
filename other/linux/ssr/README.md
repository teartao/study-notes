# SSR加速器对比

## 结论

| 名称                  | 速度   | 兼容性               | 资源占用 | 配置推荐(RAM) | 稳定性(满分10) | 安装难度                    |
| --------------------- | ------ | -------------------- | -------- | ------------- | -------------- | --------------------------- |
| None                  | <=720p | ALL                  | 极低     | 128MB         | 9.5            | 0                           |
| BBR                   | <=4k   | Debian/Ubuntu        | 低       | 256 MB        | 8              | Debian/Ubuntu简单。CentOS难 |
| BBR_power             | ≈ 4k   | Debian/Ubuntu        | 低       |               | 7.5            | Debian/Ubuntu简单。CentOS难 |
| 锐速(SpeedServer)     | 4K+    | CentOS/Debian/Ubuntu | 中       | 512 MB        | 8.5            | 脚本多，易安装              |
| 锐速增强版(LotServer) | 4K+    | CentOS/Debian/Ubuntu | 高       | 1024 MB       | 8              | 脚本多，略复杂              |

建议内存大于512MB的都安装锐速，确实是要比BBR来得稳定，速度有保证。

**内存256MB以下的，建议安装BBR。内存太小，无法稳定运行锐速**



**引用链接**

[锐速、BBR和BBR_POWERED（魔改）对比测试](https://www.ljchen.com/archives/827)

[锐速、BBR和BBR_POWERED（魔改）对比动图](https://www.hostloc.com/thread-505637-1-1.html)



## BBR

## BBR魔改版(BBR_POWER)

## 锐速(SpeedServer)

## 锐速增强版(LotServer)



