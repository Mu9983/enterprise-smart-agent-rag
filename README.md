# RAG‑Knowledge‑QA
基于 SpringBoot + LangChain4j + Milvus + MinIO 的私有知识库RAG问答后端

## 📖项目介绍
本项目实现多用户私有知识库系统，用户上传文档，系统自动解析、切片、向量化存入向量数据库；用户提问时检索知识库作为上下文交给大模型，实现私有文档智能问答。
支持文档上传、预览下载、向量库管理、会话聊天、登录鉴权、管理员桶管理。

## ✨功能特性
- 用户模块：登录登出，JWT+RefreshToken令牌刷新，修改用户信息、头像
- 文件管理：MinIO文件上传、预览、下载、删除，支持PDF/TXT/Word等多种文档
- 文档解析：异步线程池执行文档加载、文本切片、Embedding向量化
- 向量库：Milvus存储文本向量，RAG检索增强大模型回答
- AI对话：SSE流式输出回答，Redis持久化对话历史，会话删除
- 权限控制：AOP实现超级管理员权限，桶管理功能

## 🛠️技术栈
- 后端框架：SpringBoot 3.x
- LLM框架：LangChain4j
- 向量数据库：Milvus
- 对象存储：MinIO
- 数据库：MySQL
- 缓存：Redis
- 鉴权：JWT
- 文档解析：Apache Tika、Apache PdfBox
- 通信：SSE(Server‑Sent‑Events) 流式输出

## 📐系统流程
1. 用户上传文件 → MinIO保存文件 → Mysql写入文件记录(pending)
2. 提交异步任务：读取MinIO签名URL，加载文档，递归文本切片
3. 调用Embedding模型生成向量，写入Milvus向量库，更新文件状态 success/fail
4. 用户发起对话：问题向量化，检索Milvus获取相关文档片段，组装prompt，SSE流式返回大模型回答

## 🚀快速启动
### 环境依赖
- MySQL
- Redis
- Milvus
- MinIO
- Embedding模型服务

1. 修改application配置，配置MySQL、Redis、Milvus、MinIO连接信息
2. 初始化mysql表结构
3. 启动SpringBoot应用

## 📂模块说明
- config 线程池、Milvus、MinIO、RAG、AI配置
- controller 接口控制器
- service 业务逻辑层，文件、用户、聊天、文档解析
- mapper mysql数据库操作
- repository Redis会话存储实现
- utils Jwt、MinIO、文档加载、用户上下文工具
- filter Token鉴权过滤器
- aspect 权限AOP切面

## 💡项目亮点
1. 完整RAG端到端链路实现，从文档上传到向量检索问答
2. 异步线程池处理耗时文档解析任务，不阻塞http接口
3. 文件状态机跟踪异步解析任务结果，便于前端展示进度
4. 自定义RedisChatMemoryStore实现LangChain4j会话持久化
5. JWT+RefreshToken双token，支持令牌刷新与登出失效
6. AOP注解驱动管理员权限控制

## ⚠️待优化点（开源坦诚写出，体现思考）
1. TokenFilter token解析逻辑较脆弱，可替换JSON解析；
2. 缺少全局异常处理器，异步任务异常仅日志输出；
3. 文件预览下载接口缺少文件归属权限校验；
4. 缺少接口限流、熔断，未做接口文档；
5. Milvus删除产生墓碑，需要后台定时compact；
6. 缺少单元测试；
7. RefreshToken暂未实现单用户单令牌控制。

## 📝接口示例
- POST /file/upload 文件上传
- GET /assistant/chat SSE流式对话
- POST /login 用户登录
