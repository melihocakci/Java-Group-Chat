# If you use vscode as java development
** Normal jdk installation and some extension must be installed using installation guide of vscode.

## Git version system installation for windows
** When we have use git as version constrol system vscode we need to clone private repository in order to avoid clone reository issue, We can use following
[Link to ssh key generation and addition] (https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
    git clone git@github.com:melihocakci/Java-Group-Chat.git
after cloning repo, we need to instantiate ssh agent with following commands
    Get-Service -Name ssh-agent | Set-Service -StartupType Manual
    ssh-agent
    ssh-add
    git config --global core.sshCommand C:/Windows/System32/OpenSSH/ssh.exe
    code .
your github account is now syn with vscode.