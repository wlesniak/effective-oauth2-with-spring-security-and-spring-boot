import { Log, User, UserManager , WebStorageStateStore} from 'oidc-client';

export class AuthService {
  private userManager: UserManager;
  private static instance: AuthService;

  constructor() {
    const settings = {
      authority: "http://localhost:8081/auth/realms/CryptoInc",
      client_id: "crypto-portfolio-react",
      redirect_uri: "http://localhost:3000/callback.html",
      response_type: 'id_token token',
      scope: "openid profile email"
    };
    this.userManager = new UserManager(settings);
    this.userManager.events.addUserLoaded
    Log.logger = console;
    Log.level = Log.INFO;
    
  }

  public static getInstance(): AuthService {
    if (AuthService.instance === undefined) {
      AuthService.instance = new AuthService();
    }
    return AuthService.instance;
  }

  public async isAuthenticated() : Promise<boolean>{    
    return new Promise((resolve, reject) => {
      this.getUser().then(user => {resolve((user != null && user.access_token != null && !user.expired))});;
    });
  }

  public completeLogin() {
    this.userManager.signinRedirectCallback()
       .then(user => sessionStorage.setItem("access_token",user.access_token))
       .catch((error) => console.log(error));
 }


  public getUser(): Promise<User | null> {
    return this.userManager.getUser();
  }

  public login(): Promise<void> {
    return this.userManager.signinRedirect();
  }

  public renewToken(): Promise<User> {
    return this.userManager.signinSilent();
  }

  public logout(): Promise<void> {
    return this.userManager.signoutRedirect();
  }

}