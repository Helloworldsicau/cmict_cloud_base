pipeline {
    agent {
        node {
            label 'maven'
        }
    }
    stages {
        stage('拉取代码') {
            agent none
            steps {
                container('maven') {
                    git(url: 'http://git.onecode.ict.cmcc/XINGYEZHICHENGBU/hyyftd/GANSUZHENGWUZHONGPINGGroup/backend.git', credentialsId: 'onecode-wangmaohua', branch: 'dev', changelog: true, poll: false)
                    sh 'ls -al'
                }
            }
        }

        stage('项目编译') {
            agent none
            steps {
                container('maven') {
                    sh 'cd cmict-cloud/&&mvn clean package -Dmaven.test.skip=true'
                    sh 'ls cmict-auth/target'
                }
            }
        }

        stage('default-2') {
            parallel {
                stage('构建cmict-auth镜像') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'docker build -t cmict-auth:latest -f cmict-auth/Dockerfile ./cmict-auth/'
                        }

                    }
                }

                stage('构建cmict-gateway镜像') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'docker build -t cmict-gateway:latest -f cmict-gateway/Dockerfile  ./cmict-gateway/'
                        }

                    }
                }

                stage('构建cmict-server-system镜像') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'docker build -t cmict-server-system:latest -f cmict-server/cmict-server-system/Dockerfile  ./cmict-server/cmict-server-system/'
                        }

                    }
                }

                stage('构建cmict-server-activiti镜像') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'docker build -t cmict-activiti:latest -f cmict-server/cmict-activiti/Dockerfile  ./cmict-server/cmict-activiti/'
                        }

                    }
                }
                stage('构建BI报表服务镜像') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'docker build -t cmict-report:latest -f cmict-server/cmict-report/Dockerfile  ./cmict-server/cmict-report/'
                        }

                    }
                }

            }
        }
stage('default-3') {
      parallel {
        stage('推送cmict-auth镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'onecode-wangmaohua' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                sh 'docker tag cmict-auth:latest $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-auth:SNAPSHOT-$BUILD_NUMBER'
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-auth:SNAPSHOT-$BUILD_NUMBER'
              }

            }

          }
        }
                stage('推送cmict-gateway镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'onecode-wangmaohua' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                sh 'docker tag cmict-gateway:latest $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-gateway:SNAPSHOT-$BUILD_NUMBER'
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-gateway:SNAPSHOT-$BUILD_NUMBER'
              }

            }

          }
        }
                        stage('推送cmict-server-system镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'onecode-wangmaohua' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                sh 'docker tag cmict-server-system:latest $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-server-system:SNAPSHOT-$BUILD_NUMBER'
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-server-system:SNAPSHOT-$BUILD_NUMBER'
              }

            }

          }
        }

                                stage('推送cmict-server-activiti镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'onecode-wangmaohua' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                sh 'docker tag cmict-activiti:latest $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-activiti:SNAPSHOT-$BUILD_NUMBER'
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-activiti:SNAPSHOT-$BUILD_NUMBER'
              }

            }

          }
        }
        stage('推送BI报表镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'onecode-wangmaohua' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                sh 'docker tag cmict-report:latest $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-report:SNAPSHOT-$BUILD_NUMBER'
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/cmict-report:SNAPSHOT-$BUILD_NUMBER'
              }

            }

          }
        }


      }
    }

            stage('default-4') {
            parallel {
                stage('cmict-auth 部署到dev环境') {
                    agent none
                    steps {
                        kubernetesDeploy(configs: 'cmict-auth/deploy/**', enableConfigSubstitution: true, kubeconfigId: "kubeconfig")
                    }
                }

                stage('cmict-gateway 部署到dev环境') {
                    agent none
                    steps {
                        kubernetesDeploy(configs: 'cmict-gateway/deploy/**', enableConfigSubstitution: true, kubeconfigId: "kubeconfig")
                    }
                }

                stage('cmict-server-activiti部署到dev环境') {
                    agent none
                    steps {
                        kubernetesDeploy(configs: 'cmict-server/cmict-activiti/deploy/**', enableConfigSubstitution: true, kubeconfigId: "kubeconfig")
                    }
                }

                stage('cmict-server-system部署到dev环境') {
                    agent none
                    steps {
                        kubernetesDeploy(configs: 'cmict-server/cmict-server-system/deploy/**', enableConfigSubstitution: true, kubeconfigId: "kubeconfig")
                    }
                }
                stage('cmict-report部署到dev环境') {
                    agent none
                    steps {
                        kubernetesDeploy(configs: 'cmict-server/cmict-report/deploy/**', enableConfigSubstitution: true, kubeconfigId: "kubeconfig")
                    }
                }

            }
        }
  }
       environment {
        DOCKER_CREDENTIAL_ID = 'dockerhub-id'
        GITHUB_CREDENTIAL_ID = 'github-id'
        KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
        REGISTRY = 'image.onecode.ict.cmcc:31104'
        DOCKERHUB_NAMESPACE = 'gansugovernmen'
        GITHUB_ACCOUNT = 'kubesphere'
        APP_NAME = 'devops-java-sample'
    }
    parameters {
        string(name: 'TAG_NAME', defaultValue: '', description: '')
    }
}