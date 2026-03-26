param(
    [string]$BaseUrl = "http://localhost:8080"
)

$endpoints = @("/health/ping", "/health/db")

foreach ($path in $endpoints) {
    $url = "$BaseUrl$path"
    try {
        $response = Invoke-WebRequest -UseBasicParsing -Uri $url -TimeoutSec 5
        Write-Output ("OK " + $path + " -> " + $response.Content)
    }
    catch {
        Write-Output ("FAIL " + $path + " -> " + $_.Exception.Message)
        exit 1
    }
}

