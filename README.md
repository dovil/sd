一、Google云安装方法 

1.1获取root命令<br> 
sudo -i<br> 

1.2开通ssh<br> 
		设置密码<br> 
Passwd root<br> 
修改配置文件<br> 
vi /etc/ssh/ssd_config<br> 
修改<br> 
PermitRootLogin yes<br> 
PasswordAuthentication yes<br> 
重启服务<br> 
Service sshd restart<br> 

1.3先删除默认的时区设置<br> 
rm -rf /etc/localtime<br> 
替换上海作为默认<br> 
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime<br> 

1.4防火墙修改<br> 
查看防火墙状态<br> 
systemctl status firewalld.service<br> 
关闭防火墙<br> 
systemctl stop firewalld.service<br> 
禁止防火墙<br> 
systemctl disable firewalld.service<br> 
查看防火墙状态<br> 
systemctl status firewalld.service<br> 

1.5CentOS相关补丁<br> 
如果没有wget，可以先安装wget<br> 
yum -y install wget<br> 

1.6安装BBR plus加速<br> 
wget "https://github.com/chiakge/Linux-NetSpeed/raw/master/tcp.sh" && chmod +x tcp.sh && ./tcp.sh<br> 

1.7开启VPS的80和443端口：<br> 
systemctl start firewalld.service<br> 
firewall-cmd --zone=public --add-port=80/tcp --permanent<br> 
firewall-cmd --zone=public --add-port=443/tcp --permanent<br> 
firewall-cmd --reload<br> 

2. Trojan 一键脚本<br> 
2.1 Trojan IP安装<br> 
wget -N --no-check-certificate https://raw.githubusercontent.com/mark-hans/trojan-wiz/master/ins.sh && chmod +x ins.sh && sudo bash ins.sh<br> 

复制/home/trojan/ca-cert.pem和client.json到trojan客服端目录<br> 

2.2Trojan 一键脚本+TLS1.3<br> 
先安装curl<br> 
yum -y install curl<br> 

运行<br> 
curl -0 https://raw.githubusercontent.com/atrandys/trojan/master/trojan_mult.sh && chmod +x trojan_mult.sh && ./trojan_mult.sh<br> 

curl -0 https://raw.githubusercontent.com/dovil/sd/master/Trajon-mu.sh && chmod +x trojan-mu.sh && ./trojan-mu.sh<br> 
伪装网站地址，可自行替换 <br> 
/usr/share/nginx/html<br> 

3. 启动服务(IP的服务）<br> 
systemctl start trojan-gfw<br> 

4. 查看运行状态（IP的服务）<br> 
systemctl status trojan-gfw<br> 

二、混合安装<br> 
1.V2ray 一键安装<br> 

bash <(curl -s -L https://git.io/v2ray.sh)<br> 

systemctl start v2ray<br> 
systemctl stop v2ray<br> 
systemctl restart v2ray<br> 

查看状态<br> 
systemctl status v2ray<br> 


2Trojan V2ray组合搭建<br> 
2.1 Trojan &Nginx 一键安装脚本<br> 
curl -O https://raw.githubusercontent.com/atrandvs/trojan/master/trojan_centos7.sh && chmod +x trojan_centos7.sh && ./trojan_centos7.sh<br> 
2.2 V2ray 官方一键安装脚本<br> 
bash <(curl -L -s https://install.direct/go.sh)<br> 
2.3Trojan&nginx增加V2ray<br> 
  1）修改相关配置文件<br> 
   vi /etc/nginx/conf.d/default.conf <br> 
增加<br> 
location /v2/<br> 
	{<br> 
	proxy_redirect off;<br> 
	proxy_pass http://127.0.0.1:11142;<br> 
	proxy_http_version 1.1;<br> 
	proxy_set_header Upgrade $http_upgrade;<br> 
	proxy_set_header Connection "upgrade";<br> 
	proxy_set_header Host $http_host;<br> 
	}<br> 
  vi /etc/v2ray/config.json<br> 
{<br> 
"inbounds":[<br> 
	{<br> 
	"port”:11142,<br> 
	  "listen":"127.0.0.1",<br> 
	  "tag":"vmess-in",<br> 
	  "protocol":"vmess",<br> 
	  "settings":{<br> 
	     "clients":[<br> 
	     {<br> 
	     "id":"393b6a99-2ee6-46ea-a0ff-4962a321f15a”,<br> 
	     "alterID”:64<br> 
	     }<br> 
	    ]<br> 
	   },<br> 
	 "streamSettings":{<br> 
	   "network":"ws",<br> 
	   "wsSettings":{<br> 
	     "path":"/v2/"<br> 
	     }<br> 
	   }<br> 
	 }<br> 
	],<br> 
	"outbounds":[<br> 
	   {<br> 
	   "protocol":"freedom",<br> 
	   "settings":{},<br> 
	   "tag":"blocked"<br> 
	   }<br> 
	  ],<br> 
	"routing":{<br> 
	  "domainStrategy":"AsIs",<br> 
	  "ruules":[<br> 
	     {<br> 
	        "type":"field",<br> 
		"inboundTag":[<br> 
		   "vmess-in"<br> 
		   ],<br> 
		   "outboundTag":"direct"<br> 
	      }<br> 
	   ]<br> 
	 }<br> 
}<br> 

systemctl start v2ray<br> 
systemctl enable v2ray<br> 
systemctl restart nginx<br> 

三、SSR配置设置<br> 
SSR配置<br> 
配置文件增加<br> 
“Redirect” : [ “*:443#127.0.0.1:端口号”],<br> 

http://域名:80{<br> 

 redir https://域名:端口{url}<br> 

}<br> 

https://域名:端口{

	gzip
	tls 邮箱
	root /var/www/
}
