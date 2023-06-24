// Copyright 2021 The etcd Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package cmd

import (
	"fmt"
	"os"

	"github.com/spf13/cobra"
)

const (
	EtcdVersionAnnotation = "etcd_version"
)

func RootCmd() *cobra.Command {
	var annotation string
	cmd := &cobra.Command{
		Use:   "proto-annotation",
		Short: "Proto-annotations prints a dump of annotations used by all protobuf definitions used by Etcd.",
		Long: `Tool used to extract values of a specific proto annotation used by protobuf definitions used by Etcd.
Created to ensure that all newly introduced proto definitions have a etcd_version_* annotation, by analysing diffs between generated by this tool.

Proto annotations is printed to stdout in format:
<Field full name>: "<etcd_version>"


For example:
'''
etcdserverpb.Member: "3.0"
etcdserverpb.Member.ID: ""
etcdserverpb.Member.clientURLs: ""
etcdserverpb.Member.isLearner: "3.4"
etcdserverpb.Member.name: ""
etcdserverpb.Member.peerURLs: ""
'''

Any errors in proto will be printed to stderr.
`,
		RunE: func(cmd *cobra.Command, args []string) error {
			return runProtoAnnotation(annotation)
		},
	}
	cmd.Flags().StringVar(&annotation, "annotation", "", "Specify what proto annotation to read. Options: etcd_version")
	cmd.MarkFlagRequired("annotation")
	return cmd
}

func runProtoAnnotation(annotation string) error {
	var errs []error
	switch annotation {
	case EtcdVersionAnnotation:
		errs = printEtcdVersion()
	default:
		return fmt.Errorf("unknown annotation %q. Options: %q", annotation, EtcdVersionAnnotation)
	}
	if len(errs) != 0 {
		for _, err := range errs {
			fmt.Fprintln(os.Stderr, err)
		}
		return fmt.Errorf("failed reading anotation")
	}
	return nil
}
