#!/bin/bash

VPN_HOST=dialin.htwg-konstanz.de
VPN_SUBNET="141.37.0.0/16"
CREDENTIALS_FILE="/etc/ppp/chap-secrets"
CONNECTION_FILE="/etc/ppp/peers/$VPN_HOST"
ROUTE_FILE="/etc/ppp/ip-up.d/route-traffic"

echo "######################################################"
echo "#                  V P N  -  H T W G                 #"
echo "#        C O N F I G U R E  A N D  S T A R T         #"
echo "######################################################"

# Install dependencies
echo "Install dependencies"
sudo apt-get install -y -qq pptp-linux network-manager-pptp

# Configure credentials
if [ -z "$HTWG_VPN_USER" ] || [ -z "$HTWG_VPN_PASSWORD" ]; then
  echo "Please provide HTWG_VPN_USER and HTWG_VPN_PASSWORD" &1>2
  exit 1
fi
if sudo grep -q $VPN_HOST $CREDENTIALS_FILE; then
  echo "Credentials already exists"
else
  echo "Write credentials to $VPN_HOST $CREDENTIALS_FILE"
  echo "$HTWG_VPN_USER    PPTP    $HTWG_VPN_PASSWORD    $VPN_HOST" >> $CREDENTIALS_FILE
fi

# Add connection
echo "Add connection"
cat > $CONNECTION_FILE <<- EOM
pty "pptp $VPN_HOST --nolaunchpppd"
name $HTWG_VPN_USER
remotename PPTP
require-mppe-128
file /etc/ppp/options.pptp
ipparam $VPN_HOST
EOM

# Extend route entries
echo "Route traffic over pptp connection"
cat > $ROUTE_FILE <<- EOM
#!/bin/bash
route add -net "$VPN_SUBNET" dev ppp0
EOM
chmod +x $ROUTE_FILE

# Start pptp client
echo "Connect to vpn server"
pon $VPN_HOST
