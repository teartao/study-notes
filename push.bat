cd /d "%cd%"
git pull 
git add *
set /p input=ÇëÊäÈë´æµµÃèÊö:
git commit -m "%date% %time% %input%"
git push
@pause