cd /d "%cd%"
git pull 
git add *
set /p input=������浵����:
git commit -m "%date% %time% %input%"
git push
@pause