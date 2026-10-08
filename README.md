# Custom Sapling

通过数据包 JSON 定义自定义树苗。玩家右键树苗即可生长出任意方块组合的树木。


## 数据包结构

树苗定义文件放在以下路径：

```
data/<你的命名空间>/saplings/<树苗名>.json
```

例如：`data/custom_sapling/saplings/ceshi.json`

## 参数详细说明

### `sapling_tint_color`
树苗物品和方块的颜色 tint，十进制整数。用于渲染时给树苗模型上色。

### `trunk_block`
树干使用的方块 ID，格式为 `namespace:block_name`。

### `leaves_block`
树叶使用的方块 ID，格式为 `namespace:block_name`。

### `trunk_height`
树干高度的随机范围。最常用的是：
游戏会在 `min_inclusive` 到 `max_inclusive`之间随机取一个整数作为这棵树的树干高度。

### `foliage_radius`
树叶冠的半径，范围 1 到 8。数值越大，树顶的树叶团越宽。

### `trunk_placer`
树干生成器，控制树干的形状（直的、分叉的、高大的等）。需要一个 `type` 字段指定生成器类型，不同类型有不同的参数。

**`minecraft:straight_trunk_placer`** —— 直树干
- `base_height`：树干基础高度，通常填和 `trunk_height.min_inclusive` 一样的值
- `height_rand_a`：额外随机高度 A，通常填 `trunk_height.max_inclusive - trunk_height.min_inclusive`
- `height_rand_b`：额外随机高度 B，一般填 0 即可

**`minecraft:forking_trunk_placer`** —— 分叉树干（深色橡木用的）
- `base_height`、`height_rand_a`、`height_rand_b` 含义同上
- `fork_height`：从哪个高度开始分叉
- `leaves_height`：分叉上方的树叶高度

**`minecraft:giant_trunk_placer`** —— 巨树树干（云杉巨树用的，粗树干）
- `base_height`、`height_rand_a`、`height_rand_b` 含义同上


### `foliage_placer`
树叶生成器，控制树叶冠的形状和厚度。同样需要一个 `type` 字段。

**`minecraft:blob_foliage_placer`** —— 球形树叶团（原版橡树、白桦树用的）
- `radius`：树叶半径，填和 `foliage_radius` 一样的值
- `offset`：树叶冠相对于树干顶端的偏移量，0 表示正好在顶端，正数表示往下垂
- `height`：树叶冠的厚度/高度，原版常用 2 或 3

**`minecraft:spruce_foliage_placer`** —— 锥形树叶（云杉树用的）
- `radius`：底部最大半径
- `offset`：偏移量
- `height`：树尖的长度，越大树越尖

**`minecraft:pine_foliage_placer`** —— 松针形树叶（松树用的，更细更长）
- `radius`：底部半径
- `offset`：偏移量
- `height`：树尖长度

**`minecraft:dark_oak_foliage_placer`** —— 深色橡树树叶（更圆更密集）
- `radius`：半径
- `offset`：偏移量


### `valid_ground_blocks`
种植的方块，支持 字符串 和 tag


## 游戏内行为

树苗放置后有两个生长阶段（`STAGE 0` 和 `STAGE 1`）：
- `STAGE 0`：树苗幼苗，等待随机 tick 推进到下一阶段
- `STAGE 1`：树苗成熟，被随机 tick 或骨粉触发后生长为完整树木

生长需要：周围光照等级 ≥ 9（足够明亮），且有 1/7 的随机概率触发。骨粉有 45% 的概率直接推进一阶段。


## 其他相关




## 额外链接
- 颜色信息查询网站：https://gradients.app