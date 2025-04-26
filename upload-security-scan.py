import requests
import argparse
import pyfiglet


def get_token(url,password):
        URL= f'{url}/api/v2/api-token-auth/' 
        data={
            "username": "admin", 
            "password": f"{password}"
        }
        headers = {
            "content-type": "application/json"
        }
        response = requests.post(url=URL, headers=headers, json=data, verify=False)
        if response.status_code == 200:  
            token = response.json().get("token")  
            return token
        else:
            return f"Error: {response.status_code}, {response.text}"

def parsearg():
    parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument('-f', '--files', nargs='+', dest='files', help='List of file names', required=True)
    parser.add_argument('-st', '--scantypes', nargs='+', dest='scantypes', help='List of scan types (e.g., Gitleaks Scan, Retire.js Scan, ...)', required=True)
    parser.add_argument('-u', '--url', dest='url', help='url default is http://127.0.0.1:8080', required=False, default= "http://127.0.0.1:8080")
    parser.add_argument('-l', '--username', dest='username', help='username the default is admin', required=False, default= "admin")
    parser.add_argument('-p', '--password', dest='password', help='user password', required=True,)
    return parser.parse_args()

def upload_report(url,_token, scantype, file_name):
    URL = f'{url}/api/v2/import-scan/'
    headers = {
        'Authorization': f'Token {_token}'
    }
    data = {
        'active': True,
        'verified': True,
        'scan_type': scantype,
        'minimum_severity': 'Low',
        'engagement': 1
    }

    try:
        with open(file_name, 'rb') as f:
            files = {'file': f}
            response = requests.post(URL, headers=headers, files=files, data=data, verify=False)

        if response.status_code == 201:
            print('Upload successful')
            return 1
        else:
            print(f"Error {response.status_code}: {response.content}")
            return -1
    except FileNotFoundError:
        print(f"Error: File '{file_name}' not found.")
    except Exception as e:
        print(f"Unexpected error: {e}")
        return -2




def main():
    args = parsearg()
    files = args.files
    scantypes = args.scantypes
    url = args.url
    password = args.password
    token = get_token(url=url,password=password)

    if len(files) != len(scantypes):
        print("Error: The number of files must match the number of scan types.")
        return -3
    
    for file_name, scantype in zip(files, scantypes):
        print(f"Uploading {file_name} with scan type {scantype}...")
        upload_report(url=url, _token=token, scantype=scantype, file_name=file_name)



if __name__=="__main__":
    main()