$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
Push-Location $projectRoot

New-Item -ItemType Directory -Force -Path "bin" | Out-Null
& javac -d bin -sourcepath src src\com\rest\Main.java
if ($LASTEXITCODE -ne 0) {
    Pop-Location
    exit $LASTEXITCODE
}

& java -cp bin com.rest.Main
$exitCode = $LASTEXITCODE
Pop-Location
exit $exitCode
