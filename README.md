一、Google云安装方法
1.1获取root命令
sudo -i

1.2开通ssh
设置密码
Passwd root
修改配置文件
vi /etc/ssh/ssd_config
修改
PermitRootLogin yes
PasswordAuthentication yes
重启服务
Service sshd restart

1.3先删除默认的时区设置
rm -rf /etc/localtime
替换上海作为默认
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

1.4防火墙修改
#查看防火墙状态
systemctl status firewalld.service
#关闭防火墙
systemctl stop firewalld.service
#禁止防火墙
systemctl disable firewalld.service
#查看防火墙状态
systemctl status firewalld.service

1.5CentOS相关补丁
如果没有wget，可以先安装wget
yum -y install wget

1.6安装BBR plus加速
wget "https://github.com/chiakge/Linux-NetSpeed/raw/master/tcp.sh" && chmod +x tcp.sh && ./tcp.sh

1.7开启VPS的80和443端口：
systemctl start firewalld.service
firewall-cmd --zone=public --add-port=80/tcp --permanent
firewall-cmd --zone=public --add-port=443/tcp --permanent
firewall-cmd --reload

2. Trojan 一键脚本
2.1 Trojan IP安装
wget -N --no-check-certificate https://raw.githubusercontent.com/mark-hans/trojan-wiz/master/ins.sh && chmod +x ins.sh && sudo bash ins.sh

复制/home/trojan/ca-cert.pem和client.json到trojan客服端目录

2.2Trojan 一键脚本+TLS1.3
先安装curl
yum -y install curl

运行
curl -0 https://raw.githubusercontent.com/atrandys/trojan/master/trojan_mult.sh && chmod +x trojan_mult.sh && ./trojan_mult.sh

curl -0 https://raw.githubusercontent.com/dovil/sd/master/Trajon-mu.sh && chmod +x trojan-mu.sh && ./trojan-mu.sh
伪装网站地址，可自行替换 
/usr/share/nginx/html

3. 启动服务(IP的服务）
systemctl start trojan-gfw

4. 查看运行状态（IP的服务）
systemctl status trojan-gfw

二、混合安装
1.V2ray 一键安装

bash <(curl -s -L https://git.io/v2ray.sh)

systemctl start v2ray
systemctl stop v2ray
systemctl restart v2ray

查看状态
systemctl status v2ray


2Trojan V2ray组合搭建
2.1 Trojan &Nginx 一键安装脚本
curl -O https://raw.githubusercontent.com/atrandvs/trojan/master/trojan_centos7.sh && chmod +x trojan_centos7.sh && ./trojan_centos7.sh
2.2 V2ray 官方一键安装脚本
bash <(curl -L -s https://install.direct/go.sh)
2.3Trojan&nginx增加V2ray
  1）修改相关配置文件
   vi /etc/nginx/conf.d/default.conf 
增加
location /v2/
	{
	proxy_redirect off;
	proxy_pass http://127.0.0.1:11142;
	proxy_http_version 1.1;
	proxy_set_header Upgrade $http_upgrade;
	proxy_set_header Connection "upgrade";
	proxy_set_header Host $http_host;
	}
  vi /etc/v2ray/config.json
{
"inbounds":[
	{
	"port”:11142,
	  "listen":"127.0.0.1",
	  "tag":"vmess-in",
	  "protocol":"vmess",
	  "settings":{
	     "clients":[
	     {
	     "id":"393b6a99-2ee6-46ea-a0ff-4962a321f15a”,
	     "alterID”:64
	     }
	    ]
	   },
	 "streamSettings":{
	   "network":"ws",
	   "wsSettings":{
	     "path":"/v2/"
	     }
	   }
	 }
	],
	"outbounds":[
	   {
	   "protocol":"freedom",
	   "settings":{},
	   "tag":"blocked"
	   }
	  ],
	"routing":{
	  "domainStrategy":"AsIs",
	  "ruules":[
	     {
	        "type":"field",
		"inboundTag":[
		   "vmess-in"
		   ],
		   "outboundTag":"direct"
	      }
	   ]
	 }
}

systemctl start v2ray
systemctl enable v2ray
systemctl restart nginx

三、SSR配置设置
SSR配置
配置文件增加
“Redirect” : [ “*:443#127.0.0.1:端口号”],

http://域名:80{

 redir https://域名:端口{url}

}

https://域名:端口{

	gzip
	tls 邮箱
	root /var/www/
}
