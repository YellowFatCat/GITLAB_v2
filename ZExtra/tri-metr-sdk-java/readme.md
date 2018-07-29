N.B. self-signed certificates should be added to trusted keystore in java in order to contact https://sandbox.tri-metr.projects.epam.com/backend/, etc
run tests with -Djavax.net.ssl.trustStore=C:\path\to\jssecacerts
(see jssecacerts attached)

Check the EPAM resource for clarification: https://kb.epam.com/display/TRI/How+to+get+access+token+associated+with+your+user

**Essential Git commands:**

-1. To create ssh key to assess different repositories with a single key:
ssh-keygen -t rsa -C “rusergei2010@gmail.com”
0. Next you have to configure user name and email for git (specify your credentials bellow):
git config --global user.name “sergei_zheleznov”
git config --global user.email “sergei_zheleznov@epam.com”

1. >git clone https://git.epam.com/tri-metr/tri-metr-sdk-java.git
Will create a local directory for the project and check out everything into it

2. Create a new repository:
git init .
git add Readme.md
git commit -m "Initial Commit" 
git remote add github <project url> 
git push github master
3. To see remote repositories: 
git remote 
git remote -v (with paths)
4. master - branch
    origin - remote repository
    git fetch origin master
    git merge origin/master
    git push origin master
5. Create a new remote repository: >git remote add <name> url

Revert changes:
>git checkout -- file_name (remove file)
>git reset-- file_name (remove from staging area added to git files)
>git reset HEAD^^ (remove two last commits: HEAD~20 - if 20 remove)
	3. git revert <sha>      (creates commit for revert)
4. git merge --abort
    git checkout --ours / --theirs
    git diff
    Or use merge tool
5. To stash the changes:
git stash save “description”
git stash list
Bring them back: 
git stash pop
git stash apply
      -     Drop stash content: git stash drop
6. git tag release
    git tag --list
git push --tags
git checkout release1.0

Add:
git remote add <name> <url>
git remote add origin git@github.com:ser/repo.git
View:
	git remote -v
	git remote show origin
Git Blame:
	git blame file.txt (watch change)
	git bisect start

Move feature to another branch:
> git cherry-pick <sha1>    (merge a sliced changes for example with some specific changes)
