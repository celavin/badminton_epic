# 赛历系统与报名参赛说明

这份文档记录当前项目中新加入的两块能力：

1. 赛历系统
2. 报名与参赛选择

它们的目标是把原来 `simulateWholeYear()` 里硬编码的“每几个月办什么比赛”改成一个更清晰、可扩展、可观察的流程。

---

## 1. 这次新增了什么

新增的核心文件如下：

- `src/main/java/com/celavin/badmintonepic/model/dto/TournamentScheduleEntry.java`
- `src/main/java/com/celavin/badmintonepic/service/TournamentCalendarService.java`
- `src/main/java/com/celavin/badmintonepic/service/TournamentRegistrationService.java`
- `src/main/java/com/celavin/badmintonepic/service/impl/TournamentCalendarServiceImpl.java`
- `src/main/java/com/celavin/badmintonepic/service/impl/TournamentRegistrationServiceImpl.java`

接入改动主要在：

- `src/main/java/com/celavin/badmintonepic/service/TournamentManageService.java`

观察用控制台类在：

- `src/test/java/com/celavin/badmintonepic/manual/ConsoleObservationScenarios.java`

对应测试在：

- `src/test/java/com/celavin/badmintonepic/service/impl/TournamentCalendarServiceImplTest.java`
- `src/test/java/com/celavin/badmintonepic/service/impl/TournamentRegistrationServiceImplTest.java`

---

## 2. 赛历系统是什么

### 2.1 核心对象

`TournamentScheduleEntry` 表示“一条赛历项”，包含：

- `year`
- `month`
- `tournamentName`
- `level`
- `drawSize`

也就是说，现在一项赛事不再只是临时拼出来的参数，而是先变成一条“赛历计划”，之后再进入报名和比赛阶段。

### 2.2 赛季模板

当前赛历实现是 `TournamentCalendarServiceImpl`，它内置了一个年度模板：

- 1 月：Challenge
- 2 月：Challenge
- 3 月：Challenge
- 4 月：Challenge
- 5 月：Elite
- 6 月：Major
- 7 月：Challenge
- 8 月：Challenge
- 9 月：Challenge
- 10 月：Challenge
- 11 月：Elite
- 12 月：Major

每个月当前只生成 1 站比赛。

### 2.3 签表规模

当前规则：

- `MAJOR`：8 人签表
- `ELITE`：16 人签表
- 其他级别：16 人签表

后面如果你要扩成“同月多站赛事”“不同级别不同签表规模”，改这里就行。

---

## 3. 报名与参赛选择是什么

### 3.1 入口

报名逻辑统一在 `TournamentRegistrationServiceImpl#selectParticipants(...)`。

输入：

- 一条赛历项 `TournamentScheduleEntry`
- 当前所有球员 `List<Player>`

输出：

- 这站比赛最终参赛名单 `List<Player>`

### 3.2 当前筛选逻辑

先按积分从高到低排序。

然后根据赛事等级分成两段：

- 一部分是“直接入围”
- 一部分是“候选池中随机抽取”

当前规则：

#### MAJOR

- 前 4 名直接入围
- 再从后续候选池里补满到 8 人

#### ELITE

- 前 8 名直接入围
- 再从后续候选池里补满到 16 人

#### CHALLENGE / 其他

- 直接入围人数更少
- 候选池更大
- 更容易让中后段球员参加

### 3.3 为什么这样设计

这是一个“先能跑、后细化”的版本，目的是先实现：

- 高级别赛事更偏向高积分球员
- 低级别赛事保留更多随机性和流动性
- 不是每站都全员自动参赛

现在还没有做的内容包括：

- 士气影响报名
- 年龄/体能/伤病影响报名
- 国家保护名额
- 卫冕冠军/外卡机制
- 真正意义上的“主动报名”和“拒绝参赛”

---

## 4. 年度模拟现在怎么跑

当前 `TournamentManageService#simulateWholeYear(List<Player>)` 的流程是：

1. 读取当前游戏时间
2. 从当前月份开始，往后连续模拟 12 个月
3. 每个月调用赛历服务拿到当月赛事
4. 每条赛事调用报名服务选出参赛球员
5. 用 `runAndSaveTournament(...)` 执行比赛并存档
6. 月份推进 1 次

也就是说，现在年度模拟不再是纯硬编码循环，而是：

`时间 -> 赛历 -> 报名 -> 开赛 -> 存档 -> 推进时间`

这就是这次改动最核心的结构升级。

---

## 5. 如何在控制台观察结果

因为现在还没有前端，所以我保留了一套“观察场景类”：

- `ConsoleObservationScenarios`

这个类故意没有命名成 `*Test`，所以：

- IDE 里可以单独运行某个方法
- `mvn test` 默认不会跑它

### 可以直接点跑的方法

#### `watchRank()`

看当前积分榜。

#### `watchRecentTournaments()`

看最近 12 站赛事结果。

#### `watchChampionStats()`

看冠军统计。

#### `watchCurrentMonthSchedule()`

看当前月份赛历里安排了什么比赛。

#### `watchCurrentMonthRegistrations()`

看当前月份比赛的报名名单。

#### `simulateCurrentMonthTournament()`

看当前月份比赛的参赛名单，并立即模拟这站比赛。

#### `simulateWholeYearAndWatchRecentResults()`

直接模拟未来 12 个月，再打印最近赛事和积分榜。

---

## 6. 为什么观察类之前会报错

当前分支里主配置文件 `src/main/resources/application.yaml` 已经不在版本控制中。

所以我补了一份仅供测试环境使用的配置：

- `src/test/resources/application.yaml`

作用：

- 让 `@SpringBootTest` 能连到你本地 PostgreSQL
- 让观察类在 IDE 里单独运行时不至于因为缺数据源配置而启动失败

注意：

这份配置默认使用：

- `jdbc:postgresql://localhost:5434/badminton_epic?currentSchema=public`
- 用户名 `postgres`
- 密码 `123456`

如果你本地数据库不是这个配置，需要改这里。

---

## 7. 当前版本的限制

这套系统现在是“轻量版”。

### 已经实现

- 月份级赛历
- 按赛事等级区分报名策略
- 从当前时间连续模拟 12 个月
- 控制台观察赛历、报名、赛事结果

### 还没实现

- 一个月多站比赛
- 固定真实赛事名称和固定举办月份
- 外卡、资格赛、卫冕冠军直通
- 球员主动参赛意愿
- 伤病、疲劳、赛程冲突
- 报名后弃赛
- 不同赛事不同国家分布/报名门槛

---

## 8. 后续最推荐的扩展方向

如果要继续做，我建议按这个顺序来：

1. 一个自然月支持多站赛事
2. 报名逻辑引入士气、年龄、体能
3. 赛历从模板升级为固定赛事表
4. 加资格赛 / 外卡 / 直通名额
5. 做前端页面展示赛历、报名名单和赛事结果

---

## 9. 一句话总结

现在项目里的赛事流程已经从“写死循环开赛”升级成了“按赛历安排赛事，再根据报名规则决定谁来打”。

虽然还是第一版，但结构已经成型，后面继续加玩法时，不需要再从头推翻。
