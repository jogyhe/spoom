# spoom

## introduction

　　`spoom` is spoomlan's private project,include **backend**-**frontend** modules.
This is a admin system.



## Todolist

- token验证功能    √
- 使用缓存保存token等信息
- 封装相关component
- 完成登录功能
- 编写util
- 将后台界面使用zhengAdmin重写
- 后台接口定义
- 前台编写view

![git flow](https://pic.zz173.com/inset/git-flow4.png)

###开始Release

```
git checkout -b release-0.1.0 develop

# Optional: Bump version number, commit
# Prepare release, commit
```
###完成Release
```
git checkout master
git merge --no-ff release-0.1.0
git push

git checkout develop
git merge --no-ff release-0.1.0
git push

git branch -d release-0.1.0

# If you pushed branch to origin:
git push origin --delete release-0.1.0   


git tag -a v0.1.0 master
git push --tags
```



## Build Setup

``` bash
backend:
maven import & run SpoomApplication.class
```

``` bash
frontend:
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```
