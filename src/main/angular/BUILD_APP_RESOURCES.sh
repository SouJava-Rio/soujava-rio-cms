#!/bin/sh

echo "
   _____                        _                               __  _____    _____    ____   __
  / ____|                      | |                             / / |  __ \  |_   _|  / __ \   \ \\
 | (___     ___    _   _       | |   __ _  __   __   __ _     | |  | |__) |   | |   | |  | |  | |
  \___ \   / _ \  | | | |  _   | |  / _  | \ \ / /  / _  |   / /   |  _  /    | |   | |  | |   \ \\
  ____) | | (_) | | |_| | | |__| | | (_| |  \ V /  | (_| |   \ \   | | \ \   _| |_  | |__| |   / /
 |_____/   \___/   \__,_|  \____/   \__,_|   \_/    \__,_|    | |  |_|  \_\ |_____|  \____/   | |
                                                              \_\                            /_/
"

echo "[>] INICIANDO BUILD DA APLICACAO ANGULAR 4"

ng build -bh=/WEB-VIEW/

echo "[>] FINALIZANDO BUILD DA APLICACAO ANGULAR 4 E COPIANDO PARA PASTA (src/main/resources/WEB-VIEW)"

echo "8)"
