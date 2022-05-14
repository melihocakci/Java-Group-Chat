# If you use vscode as java development
**Normal jdk installation and some extension must be installed using installation guide of vscode.**

## Git version system installation for windows
**When we have use git as version constrol system vscode we need to clone private repository in order to avoid clone reository issue,**

<p>We can use following;</p>

##[Link to ssh key generation and addition](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
## Cloning and ssh agent activation
    git clone git@github.com:melihocakci/Java-Group-Chat.git
    Get-Service -Name ssh-agent | Set-Service -StartupType Manual
    ssh-agent
    ssh-add
    git config --global core.sshCommand C:/Windows/System32/OpenSSH/ssh.exe
    code .
your github account is now sync with vscode.
